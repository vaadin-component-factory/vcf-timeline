/*-
 * #%L
 * Timeline
 * %%
 * Copyright (C) 2021 Vaadin Ltd
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
/**
 * timeline-arrows
 * https://github.com/javdome/timeline-arrows
 *
 * Class to easily draw lines to connect items in the vis Timeline module.
 *
 * @version 3.1.0
 * @date    2021-04-06
 *
 * @copyright (c) Javi Domenech (javdome@gmail.com) 
 *
 *
 * @license
 * timeline-arrows is dual licensed under both
 *
 *   1. The Apache 2.0 License
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   and
 *
 *   2. The MIT License
 *      http://opensource.org/licenses/MIT
 *
 * timeline-arrows may be distributed under either license.
 */

 export default class Arrow {

    constructor(timeline) {
        this._svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
        this._timeline = timeline;

        this._arrowHeadPath = document.createElementNS(
            "http://www.w3.org/2000/svg",
            "path"
        );
        
        this._dependency = [];
        this._dependencyPath = [];
        this._minItemHeight = null;
        this._initialize();
    }
  
    _initialize() {
        //Configures the SVG layer and add it to timeline
        this._svg.style.position = "absolute";
        this._svg.style.top = "0px";
        this._svg.style.height = "100%";
        this._svg.style.width = "100%";
        this._svg.style.display = "block";
        this._svg.style.zIndex = "-1"; // Should it be above or below? (1 for above, -1 for below)
        this._svg.style.pointerEvents = "none"; // To click through, if we decide to put it above other elements.
        this._timeline.dom.center.appendChild(this._svg);
    }
    
    _createPath(){
        //Add a new path to array dependencyPath and to svg
        let somePath = document.createElementNS(
            "http://www.w3.org/2000/svg",
            "path"
          );
          somePath.setAttribute("d", "M 0 0");
          somePath.style.stroke = "grey";
          somePath.style.strokeWidth = "3px";
          somePath.style.fill = "none";
          somePath.style.pointerEvents = "auto";
          this._dependencyPath.push(somePath);
          this._svg.appendChild(somePath);
    }

    _drawDependencies() {
        //Create paths for the started dependency array
        for (let i = 0; i < this._dependency.length; i++) {
            this._drawArrows(this._dependency[i], i);
        }
    }

    _drawArrows(dep, index) {
        if( (this._timeline.itemsData.get(dep.id_item_1) !== null) && (this._timeline.itemsData.get(dep.id_item_2) !== null) ) {
            var bothItemsExist = true;
        } else {
            var bothItemsExist = false;
        }

        if (bothItemsExist) {
            var item_1 = this._getItemPos(this._timeline.itemSet.items[dep.id_item_1]);
            var item_2 = this._getItemPos(this._timeline.itemSet.items[dep.id_item_2]);
            // As demo, we put an arrow between item 0 and item1, from the one that is more on left to the one more on right.
            if (item_2.mid_x < item_1.mid_x) [item_1, item_2] = [item_2, item_1]; 
            this._dependencyPath[index].setAttribute("id", dep.id);
            this._dependencyPath[index].setAttribute(
            "d",
            "M " +
                item_1.right +
                " " +
                item_1.mid_y +
                " C " +
                (item_1.right) +
                " " +
                item_1.mid_y +
                " " +
                (item_2.left) +
                " " +
                item_2.mid_y +
                " " +
                item_2.left +
                " " +
                item_2.mid_y
            );
            // Adding the title if property title has been added in the dependency
            if (dep.hasOwnProperty("title")) {
                this._dependencyPath[index].innerHTML = "<title>" +dep.title +"</title>"
            }
        } 
    }

    //Función que recibe in Item y devuelve la posición en pantalla del item.
    _getItemPos (item) {
        let left_x = item.left;

        let top_y = item.parent.top + item.parent.height - item.top - item.height;
        return {
            left: left_x,
            top: top_y,
            right: left_x + item.width,
            bottom: top_y + item.height,
            mid_x: left_x + item.width / 2,
            mid_y: item.top + this._minItemHeight / 2,
            width: item.width,
            height: item.height,
        }
    }

    addArrow (dep) {
        this._dependency.push(dep);
        this._createPath();
        this._timeline.redraw();
    }

    getArrow (id) {
        for (let i = 0; i < this._dependency.length; i++) {
            if (this._dependency[i].id == id) {
                return this._dependency[i];
            }
        }
        return null;
    }
    
    //Función que recibe el id de una flecha y la elimina.
    removeArrow(id) {
        for (let i = 0; i < this._dependency.length; i++) {
            if (this._dependency[i].id == id) var index = i;
        }

        var list = document.querySelectorAll("#" +this._timeline.dom.container.id +" path");

        this._dependency.splice(index, 1); //Elimino del array dependency
        this._dependencyPath.splice(index, 1); //Elimino del array dependencyPath
        
        list[index].parentNode.removeChild(list[index]); //Lo elimino del dom
    }

    //Función que recibe el id de un item y elimina la flecha.
    removeArrowbyItemId(id) {
        let listOfRemovedArrows = [];
        for (let i = 0; i < this._dependency.length; i++) {
            if ( (this._dependency[i].id_item_1 == id) || (this._dependency[i].id_item_2 == id) ) {
                listOfRemovedArrows.push(this._dependency[i].id);
                this.removeArrow(this._dependency[i].id);
                i--;
            } 
        }
        return listOfRemovedArrows;
    }

    _clearAllArrows(){
        this._svg.replaceChildren("");
        this.dependencies = [];
        this._dependencyPath = [];
        this._minItemHeight = null;
    }

    setDependencies(dependencies) {
        if(this._dependencyPath.length > 0) {
            this._clearAllArrows();
        }
        
        this._dependency = dependencies;

        //Create paths for the started dependency array
        for (let i = 0; i < this._dependency.length; i++) {
            this._createPath();
        }

        this._minItemHeight = this._getMinItemHeight();

        this._drawDependencies();
    }

    _getMinItemHeight(){
        var minHeight = Number.MAX_VALUE;
        this._timeline.itemsData.forEach(item => {
        let height = this._timeline.itemSet.items[item.id].height;    
            if(height < minHeight){
                minHeight = height;
            }
        });
        return minHeight;
    }

  }
