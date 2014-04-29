function attemptMazeMove()
{
    $.getJSON( "maze/move", {fromLocation: "{x:0,y:0}", direction: "EAST"}, function( data )
    {
        updateLog("now at location [" + data.x + "," + data.y + "]");

        if(data.exit)
        {
            updateLog("exit reached");
        }
    }).fail(function()
    {
        updateLog("failed move");
    });
}

function getEntrance()
{
    $.getJSON( "maze/entrance", function( data )
    {
        updateLog("entrance is at [" + data.x + "," + data.y + "]");
    });
}

function setMaze()
{
    updateLog("loading maze one.maze");
    $.post( "maze/maze", {file: "one.maze"});
}

