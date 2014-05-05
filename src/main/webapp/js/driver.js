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
                            moveExplorer(location, outcome.location).
                                done(function()
                                {
                                    explorerLocation = outcome.location;

                                    if(outcome.exitReached)
                                    {
                                        updateLog("Exit reached!");
                                        exitMaze();
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