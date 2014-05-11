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

        maze.getMazes()
        .then(function afterGetMazes(data)
        {
            return maze.setDefaultMaze();
        })
        .then(function afterSetDefaultMaze(mazeDefinition)
        {
            graphics.drawMaze($.parseJSON(mazeDefinition));
            thisDriver.startMaze();
        })
        .done();
    };

    this.setMaze = function setMaze(mazeName)
    {
        var thisDriver = this;

        maze.setMaze(mazeName)
        .then(function afterSetMaze(mazeDefinition)
        {
            graphics.drawMaze($.parseJSON(mazeDefinition));
            thisDriver.startMaze();
        })
        .done();
    };

    this.startMaze = function startMaze()
    {
        var mazeEntrance = null;

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
        .then(function()
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

    this.moveCycle = function moveCycle()
    {
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
            graphics.drawExplorerLocation(explorerLocation, theMoveOutcome.location);
            explorerLocation = theMoveOutcome.location;

            if(theMoveOutcome.exitReached)
            {
                LOG.updateLog("Exit reached!");
                explorer.exitMaze();
                endMazeTraversal();
            }
            LOG.updateLog("-------------------------");
        })
        .fail(function fail(err)
        {
            LOG.updateLog("Failed to move " + JSON.stringify(chosenDirection) + " from " + JSON.stringify(explorerLocation));
            LOG.updateLog("-------------------------");
        })
        .done();
    };
}

