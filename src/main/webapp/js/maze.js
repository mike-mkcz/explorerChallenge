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
    return $.getJSON( "maze/entrance", function( data )
    {
        updateLog("entrance is at [" + data.x + "," + data.y + "]");
    });
}

function getAvailableExits()
{
    $.getJSON( "maze/exits", {fromLocation: "{x:0,y:3}"}, function( data )
    {
        exitString = "";
        $.each(data, function( index, value ) {
            console.log(index + " - " + value.direction);
            exitString += value.direction + ",";
        });
        updateLog("exits available [" +exitString + "]");

    });
}

function setMaze()
{
    updateLog("loading maze one.maze");
    $.post( "maze/maze", {file: "one.maze"});
}

