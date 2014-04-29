function whichWay()
{
    $.getJSON( "explorer/whichWayNow", {"fromLocation":"{x:1,y:1}", "availableDirections":"[EAST,WEST]"}, function( data )
    {
        updateLog("choosing to go" + data.direction);
    });
}

function enterMaze()
{
    updateLog("entering maze");
    $.post( "explorer/enterMaze", {entrance: "{x:1,y:1}"});
}

function moveExplorer()
{
    updateLog("explorer is moving");
    $.post( "explorer/move", {fromLocation: "{x:0,y:0}", direction: "EAST"});
}

function exitMaze()
{
    updateLog("exiting maze");
    $.post( "explorer/exitMaze");
}
