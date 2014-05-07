var explorerLocation;


function startMaze()
{
    getName().
        done(function(name)
        {
            getEntrance().
                done(function(location)
                {
                    enterMaze(location).
                        done(function(data)
                        {
                            explorerLocation = location;

                            startMazeTraversal();
                            drawExplorerLocation(location, location);
                        });
                }).fail(function()
                {
                    updateLog("startMaze() failed");
                });
        });
}

function moveCycle(location)
{
    getAvailableExits(location).
        done(function(exits)
        {
            whichWay(location, exits).
                done(function(chosenDirection)
                {
                    attemptMazeMove(location, chosenDirection).
                        done(function(outcome)
                        {
                            var outcomeJSON = $.parseJSON(outcome);

                            moveExplorer(location, outcomeJSON.location).
                                done(function()
                                {
                                    explorerLocation = outcomeJSON.location;

                                    if(outcomeJSON.exitReached)
                                    {
                                        updateLog("Exit reached!");
                                        exitMaze();
                                        endMazeTraversal();
                                    }
                                    updateLog("-------------------------");
                                });
                        }).error(function(outcome)
                        {
                            updateLog("Failed to move " + JSON.stringify(chosenDirection) + " from " + JSON.stringify(location));
                            updateLog("-------------------------");
                        });
                });

    }).fail(function()
    {
        updateLog("moveCycle() failed: " + location);
    });
}

function startMazeTraversal()
{
    $(".traversalButton").removeAttr("disabled");
    redrawCurrentMaze();
}

function endMazeTraversal()
{
    $(".traversalButton").attr("disabled", "disabled");
}