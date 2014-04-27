function whichWay()
{
    $.getJSON( "explorer/whichWayNow", function( data )
    {
       console.log("i'm gonna go " + data.direction);
    });
}

function enterMaze()
{
    console.log("entering maze");
    $.post( "explorer/enterMaze", {entrance: "{x:1,y:1}"});
}

function move()
{
    console.log("posting");
    $.post( "explorer/move", {fromLocation: "{x:0,y:0}", direction: "EAST"});
}

function exitMaze()
{
    console.log("exiting maze");
    $.post( "explorer/exitMaze");
}
