
function Explorer()
{
}

Explorer.prototype.explorerName = '';

Explorer.prototype.whichWay = function whichWay(fromLocation, availableDirections)
{
    updateLog(this.explorerName + ": which way shall I go?");
    return $.getJSON( "explorer/whichWayNow", {fromLocation: JSON.stringify(fromLocation), availableDirections:JSON.stringify(availableDirections)}, function( data )
    {
        updateLog("choosing to go " + data);
    });
};

Explorer.prototype.getName = function getName()
{
    var thisExplorer = this;
    return $.getJSON( "explorer/name", function( name )
    {
        updateLog("Explorer entering the maze is " + name);
        thisExplorer.explorerName = name;
    });
};

Explorer.prototype.enterMaze = function enterMaze(location)
{
    updateLog(this.explorerName + " is entering the maze");
    return $.post( "explorer/enterMaze", {entrance: JSON.stringify(location)});
};

Explorer.prototype.moveExplorer = function moveExplorer(fromLocation, toLocation)
{
    var thisExplorer = this;
    updateLog(this.explorerName + " is moving from " + JSON.stringify(fromLocation) + " to " + JSON.stringify(toLocation));
    return $.post( "explorer/move", {fromLocation: JSON.stringify(fromLocation), toLocation: JSON.stringify(toLocation)});
};

Explorer.prototype.exitMaze = function exitMaze()
{
    updateLog(this.explorerName + " is exiting the maze");
    $.post( "explorer/exitMaze");
};
