function Driver(theExplorer, theGraphics, theMaze)
{
    /*
        PRIVATE
     */

    var explorer = theExplorer;
    var graphics = theGraphics;
    var maze = theMaze;
    var explorerLocation = null;
    var isMoving = false;
    var defaultMoveDelayMs = 500;
    var moveDelayMs = defaultMoveDelayMs;
    var totalMoves = 0;

    var startMazeTraversal = function startMazeTraversal()
    {
        $(".traversalButton").removeAttr("disabled");
        graphics.redrawCurrentMaze();
        moveDelayMs = defaultMoveDelayMs;
        LOG.clear();
    };

    var updateTotalMoves = function updateTotalMoves()
    {
        $(".move-count-value").html(totalMoves);
    };

    var moveCycle = function moveCycle()
    {
        if(!isMoving)
        {
            return;
        }

        var theChosenDirection = null;
        var theMoveOutcome = null;

        maze.getAvailableExits(explorerLocation)
            .then(function afterExits(exits)
            {
                return explorer.whichWay(explorerLocation, exits);
            })
            .then(function afterWhichWay(chosenDirection)
            {
                theChosenDirection = chosenDirection;
                return maze.attemptMazeMove(explorerLocation, chosenDirection);
            })
            .then(function afterAttemptMove(outcomeJSON)
            {
                theMoveOutcome = $.parseJSON(outcomeJSON);
                return explorer.moveExplorer(explorerLocation, theMoveOutcome.location);
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
                graphics.drawExplorerLocation(explorerLocation, theMoveOutcome.location);
                explorerLocation = theMoveOutcome.location;
                LOG.updateLog("-------------------------");
                updateTotalMoves();

                if(theMoveOutcome.exitReached)
                {
                    LOG.updateLog("Exit reached!");
                    explorer.exitMaze();
                    endMazeTraversal();
                }
                else
                {
                    setTimeout(moveCycle, moveDelayMs);
                }

            })
            .fail(function fail(err)
            {
                LOG.updateLog("Failed to move " + JSON.stringify(chosenDirection) + " from " + JSON.stringify(explorerLocation));
                LOG.updateLog("-------------------------");
            })
            .done();
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
            var mazeDefinitionObjext = $.parseJSON(mazeDefinition);

            if(mazeDefinitionObjext.key != 'undefined')
            {
                maze.setKey(mazeDefinitionObjext.key, mazeDefinitionObjext.keyLocation);
            }

            graphics.drawMaze(mazeDefinitionObjext);
            thisDriver.startMaze();
        })
        .then(function afterStartMaze()
        {
            thisDriver.setExplorerHost();
        });
    };

    this.setExplorerHost = function setExplorerHost()
    {
        var chosenHost = $("#explorer-host").val();
        explorer.setExplorerHost(chosenHost);
    };

    this.startMaze = function startMaze()
    {
        var mazeEntrance = null;
        this.stopMoving();
        totalMoves = 0;
        updateTotalMoves();

        explorer.getName()
        .then(function afterGetName(name)
        {
            return maze.getEntrance();
        })
        .then(function afterGetEntrance(location)
        {
            mazeEntrance = location;
            return explorer.enterMaze(location);
        })
        .then(function afterEnterMaze()
        {
            startMazeTraversal();

            explorerLocation = mazeEntrance;
            graphics.drawExplorerLocation(mazeEntrance, mazeEntrance);
        })
        .fail(function()
        {
            LOG.updateLog("startMaze() failed");
        })
        .done();
    };

    this.startMoving = function startMoving()
    {
        $("#goButton").hide();
        $("#pauseButton").show();
        isMoving = true;
        moveCycle();
    }

    this.stopMoving = function stopMoving()
    {
        $("#pauseButton").hide();
        $("#goButton").show();
        isMoving = false;
    }

    this.changeSpeed = function changeSpeed(delta)
    {
        moveDelayMs += delta;
        moveDelayMs = Math.max(0, moveDelayMs);
    }
}

