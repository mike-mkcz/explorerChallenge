function attemptMazeMove(fromLocation, direction)
{
    updateLog("Can I move " + JSON.stringify(direction) + " from " + JSON.stringify(fromLocation) + "?");
    return $.getJSON( "maze/move", {fromLocation: JSON.stringify(fromLocation), direction: JSON.stringify(direction)}, function( data )
    {
        console.log(data);
        updateLog("Valid move!, New location is [" + data.location.x + "," + data.location.y + "]");

        if(data.exit)
        {
            updateLog("exit reached");
        }
    });
}

function getEntrance()
{
    updateLog("Finding entrance...");
    return $.getJSON( "maze/entrance", function( data )
    {
        updateLog("entrance is at [" + data.x + "," + data.y + "]");
    });
}

function getAvailableExits(location)
{
    updateLog("What exits are available from " + JSON.stringify(location) + "?");
    return $.getJSON( "maze/exits", {fromLocation: JSON.stringify(location)}, function( data )
    {
        exitString = "";
        $.each(data, function( index, value ) {
            console.log(index + " - " + value);
            exitString += value + ",";
        });
        updateLog("exits available [" +exitString + "]");

    });
}

function setMaze()
{
    updateLog("loading maze theonlywayiseast.maze");
    $.post( "maze/maze", {file: "theonlywayiseast.maze"});
}

