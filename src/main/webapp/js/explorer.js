var explorerName;

function whichWay(fromLocation, availableDirections)
{
    updateLog(explorerName + ": which way shall I go?");
    return $.getJSON( "explorer/whichWayNow", {fromLocation: JSON.stringify(fromLocation), availableDirections:JSON.stringify(availableDirections)}, function( data )
    {
        updateLog("choosing to go " + data);
    });
}

function getName()
{
    return $.getJSON( "explorer/name", function( name )
    {
        updateLog("Explorer entering the maze is " + name);
        explorerName = name;
    });
}

function enterMaze(location)
{
    updateLog(explorerName + " is entering the maze");
    return $.post( "explorer/enterMaze", {entrance: JSON.stringify(location)});
}

function moveExplorer(fromLocation, direction)
{
    updateLog(explorerName + " is moving " + JSON.stringify(direction) + " from " + JSON.stringify(fromLocation));
    return $.post( "explorer/move", {fromLocation: JSON.stringify(fromLocation), direction: JSON.stringify(direction)});
}

function exitMaze()
{
    updateLog(explorerName + " is exiting the maze");
    $.post( "explorer/exitMaze");
}
