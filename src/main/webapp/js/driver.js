function Driver(theExplorer, theGraphics, theMaze)
{
    /*
        PRIVATE
     */

    var explorer = theExplorer;
    var graphics = theGraphics;
    var maze = theMaze;
    var explorerLocation = null;

    var startMazeTraversal = function startMazeTraversal()
    {
        $(".traversalButton").removeAttr("disabled");
        graphics.redrawCurrentMaze();
    };

    var endMazeTraversal = function endMazeTraversal()
    {
        $(".traversalButton").attr("disabled", "disabled");
    };

    /*
        PRIVILEGED
     */

    this.load = function load()
    {
        var thisDriver = this;
        graphics.loadSprites();
        LOG.initialiseLog();
        maze.getMazes().
            done(function(data)
            {
                maze.setDefaultMaze().
                    done(function(mazeDefinition)
                    {
                        graphics.drawMaze($.parseJSON(mazeDefinition));
                        thisDriver.startMaze();
                    });
            });
    };

    this.setMaze = function setMaze(mazeName)
    {
        var thisDriver = this;
        maze.setMaze(mazeName).
            done(function(mazeDefinition)
            {
                graphics.drawMaze($.parseJSON(mazeDefinition));
                thisDriver.startMaze();
            });
    };

    this.startMaze = function startMaze()
    {
        explorer.getName().
            done(function(name)
            {
                maze.getEntrance().
                    done(function(location)
                    {
                        explorer.enterMaze(location).
                            done(function(data)
                            {
                                startMazeTraversal();

                                explorerLocation = location;
                                graphics.drawExplorerLocation(location, location);
                            });
                    }).fail(function()
                    {
                        LOG.updateLog("startMaze() failed");
                    });
            });
    };

    this.moveCycle = function moveCycle()
    {
        maze.getAvailableExits(explorerLocation).
            done(function(exits)
            {
                explorer.whichWay(explorerLocation, exits).
                    done(function(chosenDirection)
                    {
                        maze.attemptMazeMove(explorerLocation, chosenDirection).
                            done(function(outcomeJSON)
                            {
                                var outcome = $.parseJSON(outcomeJSON);

                                explorer.moveExplorer(explorerLocation, outcome.location).
                                    done(function()
                                    {
                                        graphics.drawExplorerLocation(explorerLocation, outcome.location);
                                        explorerLocation = outcome.location;

                                        if(outcome.exitReached)
                                        {
                                            LOG.updateLog("Exit reached!");
                                            explorer.exitMaze();
                                            endMazeTraversal();
                                        }
                                        LOG.updateLog("-------------------------");
                                    });
                            }).error(function(outcome)
                            {
                                LOG.updateLog("Failed to move " + JSON.stringify(chosenDirection) + " from " + JSON.stringify(explorerLocation));
                                LOG.updateLog("-------------------------");
                            });
                    });

            }).fail(function()
            {
                LOG.updateLog("moveCycle() failed: " + explorerLocation);
            });
    };
}

