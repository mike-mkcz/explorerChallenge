/*
    GLOBAL
 */

function generateId(){
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

var disableLog = false;
var SESSION_ID = generateId();
var LOG = new Log(disableLog);

var explorerHostList = [];
explorerHostList.push("127.0.0.1:8080");
var DRIVER = new Driver(explorerHostList, new Graphics(), new Maze(SESSION_ID));

$( document ).ready(function() {

    DRIVER.load();
});