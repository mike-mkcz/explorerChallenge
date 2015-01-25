function Driver(theExplorerHosts, theGraphics, theMaze)
{
    /*
        PRIVATE
     */

    var graphics = theGraphics;
    var maze = theMaze;
    var isMoving = false;
    var defaultMoveDelayMs = 200;
    var moveDelayMs = defaultMoveDelayMs;
    var totalMoves = 0;

    var generateId = function generateId(){
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
            var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
            return v.toString(16);
        });
    };

    var createExplorers = function createExplorer(hosts)
    {
        var explorers = [];
        $.each(hosts, function(idx, host)
        {
            explorers.push(new Explorer(generateId(), host));
        });
        return explorers;
    };
    var explorerArray = createExplorers(theExplorerHosts);

    var startMazeTraversal = function startMazeTraversal()
    {
        $(".traversalButton").removeAttr("disabled");
        graphics.redrawCurrentMaze();
        moveDelayMs = defaultMoveDelayMs;
    };

    var updateTotalMoves = function updateTotalMoves()
    {
        $(".move-count-value").html(totalMoves);
    };

    var moveCycle = function moveCycle(explorer)
    {
        if(!isMoving)
        {
            return;
        }

        var theChosenDirection = null;
        var theMoveOutcome = null;
        var currentLocation = explorer.getCurrentLocation();

        return maze.getAvailableExits(explorer.getCurrentLocation())
            .then(function afterExits(exits)
            {
                return explorer.whichWay(explorer.getCurrentLocation(), exits);
            })
            .then(function afterWhichWay(chosenDirection)
            {
                theChosenDirection = chosenDirection;
                return maze.attemptMazeMove(explorer.getCurrentLocation(), chosenDirection);
            })
            .then(function afterAttemptMove(outcomeJSON)
            {
                theMoveOutcome = $.parseJSON(outcomeJSON);
                return explorer.moveExplorer(theMoveOutcome.location);
            })
            .then(function afterMoveExplorer()
            {
                var key = maze.getKeyAtLocation(theMoveOutcome.location);

                if(key != null)
                {
                    graphics.keyFound(theMoveOutcome.location);
                    return explorer.keyFound(key, theMoveOutcome.location);
                }
                return null;
            })
            .then(function afterMayFindKey()
            {
                totalMoves++;
                graphics.drawExplorerLocation(explorer, currentLocation, theMoveOutcome.location);
                LOG.storeLog("-------------------------");
                updateTotalMoves();

                if(theMoveOutcome.exitReached)
                {
                    attemptToExitMaze(explorer, theMoveOutcome.location);
                }
                else
                {
                    explorer.moveSuccessful();
                    nextMove(explorer);
                }

            })
            .fail(function fail(err)
            {
                LOG.storeLog("Failed to move " + JSON.stringify(theChosenDirection) + " from " + JSON.stringify(explorer.getCurrentLocation()));
                LOG.storeLog("-------------------------");
                explorer.moveFailed();
                nextMove(explorer);
            });
    };

    var attemptToExitMaze = function attemptToExitMaze(explorer, location)
    {
        LOG.storeLog("Exit reached!");

        explorer.exitReached(location);

        if(maze.requiresKeyToExit())
        {
            explorer.getAcquiredKey()
                .then(function afterGetAcquiredKey(key)
                {
                    if(maze.canExitWithKey(key))
                    {
                        LOG.storeLog("Exiting maze with key");
                        exitMaze(explorer);
                    }
                    else
                    {
                        LOG.storeLog("Failed to exit maze without the required key");
                        nextMove(explorer);
                    }
                });
        }
        else
        {
            exitMaze(explorer);
        }
    };

    var exitMaze = function exitMaze(explorer)
    {
        explorer.exitMaze();
        endMazeTraversal();
    };

    var endMazeTraversal = function endMazeTraversal()
    {
        $(".traversalButton").attr("disabled", "disabled");
        LOG.writeLog();
    };

    var nextMove = function nextMove(explorer)
    {
        LOG.writeLog();

        if(explorer.shouldContinueTraversal())
        {
            setTimeout(function() {moveCycle(explorer);}, moveDelayMs);
        }
        else
        {
            LOG.storeLog("Traversal ended for " + explorer.toString());
            LOG.writeLog();
        }
    };

    /*
        PRIVILEGED
     */

    this.load = function load()
    {
        var thisDriver = this;
        $("#pauseButton").hide();
        graphics.loadSprites();

        maze.getMazes()
        .then(function afterGetMazes(data)
        {
            var firstMazeInList = $(".maze-list-item").first().find("a").text();
            return thisDriver.setMaze(firstMazeInList);
        });
    };

    this.setMaze = function setMaze(mazeName)
    {
        var thisDriver = this;

        maze.setMaze(mazeName)
        .then(function afterSetMaze(mazeDefinition)
        {
            var mazeDefinitionObject = $.parseJSON(mazeDefinition);

            if(mazeDefinitionObject.key != 'undefined')
            {
                maze.setKey(mazeDefinitionObject.key, mazeDefinitionObject.keyLocation);
            }

            graphics.drawMaze(mazeDefinitionObject);
            thisDriver.startMaze();
        });
    };

    this.startMaze = function startMaze()
    {
        LOG.clear();
        var mazeEntrance = null;
        this.stopMoving();
        totalMoves = 0;
        maze.reset();
        updateTotalMoves();

        maze.getEntrance()
        .then(function afterGetEntrance(location)
        {
            mazeEntrance = location;
            startMazeTraversal();

            $.each(explorerArray, function(idx, explorer) {

                explorer.retrieveName()
                .then(function afterRetrieveName()
                {
                    explorer.enterMaze(location)
                })
                .then(function afterEnterMaze()
                {
                    graphics.drawExplorerLocation(explorer, mazeEntrance, mazeEntrance);
                    LOG.writeLog();
                })
                .fail(function()
                {
                    LOG.storeLog(explorer.toString() + " failed to enter the maze");
                })
            });
        })
        .fail(function()
        {
            LOG.storeLog("startMaze() failed");
        });
    };

    this.startMoving = function startMoving()
    {
        $("#goButton").hide();
        $("#pauseButton").show();
        isMoving = true;

        $.each(explorerArray, function(idx, explorer)
        {
            moveCycle(explorer);
        });
    };

    this.stopMoving = function stopMoving()
    {
        $("#pauseButton").hide();
        $("#goButton").show();
        isMoving = false;
    };

    this.setSpeed = function setSpeed(speed)
    {
        if(speed == "slow")
        {
            moveDelayMs = 500;
        }
        else if(speed == "normal")
        {
            moveDelayMs = 300;
        }
        else if(speed == "fast")
        {
            moveDelayMs = 0;
        }
        LOG.storeLog("Traversal speed set to '" + speed + "'");
        LOG.writeLog();
    };

    this.updateExplorerHosts = function updateExplorerHosts()
    {
        var hostList = $("#explorer-hosts").val();
        var hostArray = hostList.split(",");
        explorerArray = createExplorers(hostArray);
    }
}

