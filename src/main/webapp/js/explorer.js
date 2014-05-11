function Explorer()
{
    /*
        PRIVATE
     */

    var explorerName = '';

    /*
        PRIVILEGED
     */

    this.whichWay = function whichWay(fromLocation, availableDirections)
    {
        updateLog(explorerName + ": which way shall I go?");
        return $.getJSON( "explorer/whichWayNow", {fromLocation: JSON.stringify(fromLocation), availableDirections:JSON.stringify(availableDirections)}, function( data )
        {
            updateLog("choosing to go " + data);
        });
    };

    this.getName = function getName()
    {
        return $.getJSON( "explorer/name", function( name )
        {
            updateLog("Explorer entering the maze is " + name);
            explorerName = name;
        });
    };

    this.enterMaze = function enterMaze(location)
    {
        updateLog(explorerName + " is entering the maze");
        return $.post( "explorer/enterMaze", {entrance: JSON.stringify(location)});
    };

    this.moveExplorer = function moveExplorer(fromLocation, toLocation)
    {
        updateLog(explorerName + " is moving from " + JSON.stringify(fromLocation) + " to " + JSON.stringify(toLocation));
        return $.post( "explorer/move", {fromLocation: JSON.stringify(fromLocation), toLocation: JSON.stringify(toLocation)});
    };

    this.exitMaze = function exitMaze()
    {
        updateLog(explorerName + " is exiting the maze");
        $.post( "explorer/exitMaze");
    };
}


