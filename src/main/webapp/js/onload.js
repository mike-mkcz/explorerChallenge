/*
    GLOBAL
 */
var LOG = new Log();
var DRIVER = new Driver(new Explorer(), new Graphics(), new Maze());

$( document ).ready(function() {

    DRIVER.load();
});