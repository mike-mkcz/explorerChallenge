
function Driver(explorer, graphics, maze)
{
    this.explorer = explorer;
    this.graphics = graphics;
    this.maze = maze;
}

Driver.prototype.explorer = null;
Driver.prototype.graphics = null;
Driver.prototype.maze = null;
Driver.prototype.explorerLocation = null;

Driver.prototype.load = function load()
{
    var thisDriver = this;
    this.graphics.loadSprites();
    initialiseLog();
    this.maze.getMazes().
        done(function(data)
        {
            thisDriver.maze.setDefaultMaze().
                    done(function(mazeDefinition)
                    {
                        thisDriver.drawAndStartMaze(mazeDefinition);
                    });
        });
};

Driver.prototype.setMaze = function setMaze(mazeName)
{
    var thisDriver = this;
    this.maze.setMaze(mazeName).
        done(function(mazeDefinition)
        {
            thisDriver.drawAndStartMaze(mazeDefinition);
        });
};

Driver.prototype.drawAndStartMaze = function(mazeDefinitionJSON)
{
    this.graphics.drawMaze($.parseJSON(mazeDefinitionJSON));
    this.startMaze();
};

Driver.prototype.startMaze = function startMaze()
{
    var thisDriver = this;
    this.explorer.getName().
        done(function(name)
        {
            thisDriver.maze.getEntrance().
                done(function(location)
                {
                    thisDriver.explorer.enterMaze(location).
                        done(function(data)
                        {
                            thisDriver.explorerLocation = location;

                            thisDriver.startMazeTraversal();
                            thisDriver.graphics.drawExplorerLocation(location, location);
                        });
                }).fail(function()
                {
                    updateLog("startMaze() failed");
                });
        });
};

Driver.prototype.moveCycle = function moveCycle()
{
    var thisDriver = this;
    this.maze.getAvailableExits(this.explorerLocation).
        done(function(exits)
        {
            thisDriver.explorer.whichWay(thisDriver.explorerLocation, exits).
                done(function(chosenDirection)
                {
                    thisDriver.maze.attemptMazeMove(thisDriver.explorerLocation, chosenDirection).
                        done(function(outcomeJSON)
                        {
                            var outcome = $.parseJSON(outcomeJSON);

                            thisDriver.explorer.moveExplorer(thisDriver.explorerLocation, outcome.location).
                                done(function()
                                {
                                    thisDriver.graphics.drawExplorerLocation(thisDriver.explorerLocation, outcome.location);
                                    thisDriver.explorerLocation = outcome.location;

                                    if(outcome.exitReached)
                                    {
                                        updateLog("Exit reached!");
                                        thisDriver.explorer.exitMaze();
                                        thisDriver.endMazeTraversal();
                                    }
                                    updateLog("-------------------------");
                                });
                        }).error(function(outcome)
                        {
                            updateLog("Failed to move " + JSON.stringify(chosenDirection) + " from " + JSON.stringify(thisDriver.explorerLocation));
                            updateLog("-------------------------");
                        });
                });

    }).fail(function()
    {
        updateLog("moveCycle() failed: " + thisDriver.explorerLocation);
    });
};

Driver.prototype.startMazeTraversal = function startMazeTraversal()
{
    $(".traversalButton").removeAttr("disabled");
    this.graphics.redrawCurrentMaze();
};

Driver.prototype.endMazeTraversal = function endMazeTraversal()
{
    $(".traversalButton").attr("disabled", "disabled");
};
