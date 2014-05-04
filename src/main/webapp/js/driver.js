var explorerLocation;


function startMaze()
{
    getEntrance().
        done(function(location)
        {
            enterMaze(location).
                done(function(data)
                {
                    explorerLocation = location;
                });
        }).fail(function()
        {
            updateLog("startMaze() failed");
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
                            moveExplorer(outcome.location, chosenDirection).
                                done(function()
                                {
                                    updateLog("move complete. Explorer is now at " + JSON.stringify(outcome.location));
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