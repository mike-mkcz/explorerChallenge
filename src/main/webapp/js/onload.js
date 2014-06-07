/*
    GLOBAL
 */

var generateId = function(){
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
};

var SESSION_ID = generateId();
var LOG = new Log();
var DRIVER = new Driver(new Explorer(SESSION_ID), new Graphics(), new Maze(SESSION_ID));

$( document ).ready(function() {

    DRIVER.load();
});