
function Maze()
{
    /*
        PRIVATE
     */

    var key = null;
    var keyLocation = null;

    /*
        PRIVILEGED
     */

    this.attemptMazeMove = function attemptMazeMove(fromLocation, direction)
    {
        LOG.updateLog("Can I move " + JSON.stringify(direction) + " from " + JSON.stringify(fromLocation) + "?");
        return $.post( "maze/move", {fromLocation: JSON.stringify(fromLocation), direction: JSON.stringify(direction)}, function( outcome )
        {
            var outcomeJson = $.parseJSON(outcome);

            LOG.updateLog("Valid move!, New location is [" + outcomeJson.location.x + "," + outcomeJson.location.y + "]");

            if(outcomeJson.exit)
            {
                LOG.updateLog("exit reached");
            }
        });
    };

   this.getEntrance = function getEntrance()
    {
        LOG.updateLog("Finding entrance...");
        return $.getJSON( "maze/entrance", function( data )
        {
            LOG.updateLog("entrance is at [" + data.x + "," + data.y + "]");
        });
    };

    this.getAvailableExits = function getAvailableExits(location)
    {
        LOG.updateLog("What exits are available from " + JSON.stringify(location) + "?");
        return $.getJSON( "maze/exits", {fromLocation: JSON.stringify(location)}, function( data )
        {
            var exitString = "";
            $.each(data, function( index, value ) {
                exitString += value + " ";
            });
            LOG.updateLog("exits available [" +exitString + "]");

        });
    };

    this.getMazes = function getMazes()
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
                            onclick: 'DRIVER.setMaze("' + value + '")'
                        })
                    })
                );
            });
        });
    };

    this.setMaze = function setMaze(mazeName)
    {
        LOG.updateLog("loading maze " + mazeName);
        return $.post( "maze/maze", {file: mazeName}, function(mazeDefinition)
        {
            $(".current-maze").val("Current maze: " + mazeName);
            $(".traversalButton").attr("disabled", "disabled");
        });
    };

    this.setKey = function setKey(theKey, theKeyLocation)
    {
        key = theKey;
        keyLocation = theKeyLocation;
    };

    this.getKeyAtLocation = function getKeyAtLocation(location)
    {
        if(_.isEqual(location, keyLocation))
        {
            return key;
        }
        return null;
    };
}


