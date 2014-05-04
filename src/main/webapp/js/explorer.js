function whichWay(fromLocation, availableDirections)
{
    updateLog("which way shall I go?");
    return $.getJSON( "explorer/whichWayNow", {fromLocation: JSON.stringify(fromLocation), availableDirections:JSON.stringify(availableDirections)}, function( data )
    {
        updateLog("choosing to go " + data);
    });
}

function getName()
{
    return $.getJSON( "explorer/name", function( data )
    {
        updateLog("Hi, my name is " + data);
    });
}

function enterMaze(location)
{
    updateLog("entering maze");
    return $.post( "explorer/enterMaze", {entrance: JSON.stringify(location)});
}

function moveExplorer(fromLocation, direction)
{
    updateLog("explorer is moving");
    return $.post( "explorer/move", {fromLocation: JSON.stringify(fromLocation), direction: JSON.stringify(direction)});
}

function exitMaze()
{
    updateLog("exiting maze");
    $.post( "explorer/exitMaze");
}
