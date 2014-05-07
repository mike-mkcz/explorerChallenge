function attemptMazeMove(fromLocation, direction)
{
    updateLog("Can I move " + JSON.stringify(direction) + " from " + JSON.stringify(fromLocation) + "?");
    return $.getJSON( "maze/move", {fromLocation: JSON.stringify(fromLocation), direction: JSON.stringify(direction)}, function( data )
    {
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
            exitString += value + " ";
        });
        updateLog("exits available [" +exitString + "]");

    });
}

function getMazes()
{
    return $.getJSON( "maze/mazes", function(data)
    {
        $('.maze-list').empty();
        $.each(data, function(index, value) {

            $('.maze-list').append(
                $('<li/>', {
                    'class': 'maze-list-item',
                    html: $('<a/>', {
                        href: '#',
                        text: value,
                        onclick: 'setMaze("' + value + '")'
                    })
                })
            );
        });
        setDefaultMaze();
    });
}

function setMaze(mazeName)
{
    updateLog("loading maze " + mazeName);
    $.post( "maze/maze", {file: mazeName}, function(mazeDefinition)
    {
        $(".current-maze").val("Current maze: " + mazeName);
        $(".traversalButton").attr("disabled", "disabled");
        drawMaze($.parseJSON(mazeDefinition));
    });
}

function setDefaultMaze()
{
    var firstMazeInList = $(".maze-list-item").first().find("a").text();
    setMaze(firstMazeInList);
}
