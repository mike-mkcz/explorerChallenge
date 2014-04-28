function attemptMazeMove()
{
    $.getJSON( "maze/move", {fromLocation: "{x:0,y:0}", direction: "EAST"}, function( data )
    {
       console.log("new location: [" + data.x + "," + data.y + "]");
       console.log("exit reached: " + data.exit);
    }).fail(function()
    {
        console.log( "failed move" );
    });
}

function getEntrance()
{
    $.getJSON( "maze/entrance", function( data )
    {
        console.log("entrance: [" + data.x + "," + data.y + "]");
    });
}

function setMaze()
{
    console.log("setting maze");
    $.post( "maze/maze", {file: "one.maze"});
}

