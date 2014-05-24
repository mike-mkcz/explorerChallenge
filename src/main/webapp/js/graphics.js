function Graphics()
{
    /*
        PRIVATE
     */

    var mazeCanvasWidth = 1000;
    var mazeCanvasHeight = 800;
    var mazeSquareSize = 20;

    var currentMazeDefinition = null;
    var mazeMaxXCoordinate = 0;
    var mazeMaxYCoordinate = 0;
    var mazeXOffsetPx = 0;
    var mazeYOffsetPx = 0;

    var explorerSprite = null;
    var wallSprite = null;
    var pathSprite = null;
    var exitSprite = null;
    var keySprite = null;
    var keyFoundSprite = null;

    var drawMazeToContext = function drawMazeToContext(mazeDefinition, ctx)
    {
        ctx.clearRect(0, 0, mazeCanvasWidth, mazeCanvasHeight);

        //border
//        ctx.beginPath();
//        ctx.strokeStyle = "#000000";
//        ctx.rect(0, 0, mazeCanvasWidth, mazeCanvasHeight);
//        ctx.stroke();

        //squares
        for(var y=0 ; y<=mazeMaxYCoordinate; y++)
        {
            for(var x=0 ; x<=mazeMaxXCoordinate ; x++)
            {
                if(mazeDefinition.grid[x][y])
                {
                    ctx.drawImage(pathSprite, absoluteXCoordinatePx(x), absoluteYCoordinatePx(y));
                }
                else
                {
                    ctx.drawImage(wallSprite, absoluteXCoordinatePx(x), absoluteYCoordinatePx(y));
                }
            }
        }

        //exit
        ctx.drawImage(exitSprite, absoluteXCoordinatePx(mazeDefinition.exit.x), absoluteYCoordinatePx(mazeDefinition.exit.y));

        //key
        if(mazeDefinition.key != null)
        {
            ctx.drawImage(keySprite, absoluteXCoordinatePx(mazeDefinition.keyLocation.x)+2, absoluteYCoordinatePx(mazeDefinition.keyLocation.y)+2);
        }
    };

    var absoluteXCoordinatePx = function absoluteXCoordinatePx(xGridPos)
    {
        return mazeXOffsetPx + xGridPos * mazeSquareSize
    };

    var absoluteYCoordinatePx = function absoluteYCoordinatePx(yGridPos)
    {
        //y origin is reversed from grid to canvas
        var yPosition = (mazeMaxYCoordinate-yGridPos);
        return mazeYOffsetPx + yPosition * mazeSquareSize
    };

    /*
        PRIVILEGED
     */

    this.setMazeDefinition = function setMazeDefinition(mazeDefinition)
    {
        currentMazeDefinition = mazeDefinition;
        var mazeCanvas = document.getElementById("maze-canvas-back");
        var mazeCanvasOutput = document.getElementById("maze-canvas-front");

        //initialise canvas size
        mazeCanvas.width = mazeCanvasWidth;
        mazeCanvas.height = mazeCanvasHeight;
        mazeCanvasOutput.width = mazeCanvasWidth;
        mazeCanvasOutput.height = mazeCanvasHeight;

        mazeMaxXCoordinate = mazeDefinition.maxXCoordinate;
        mazeMaxYCoordinate = mazeDefinition.maxYCoordinate;
        var mazeWidthPx = mazeMaxXCoordinate * mazeSquareSize;
        var mazeHeightPx = mazeMaxYCoordinate * mazeSquareSize;
        mazeXOffsetPx = (mazeCanvasWidth - mazeWidthPx)/2;
        mazeYOffsetPx = (mazeCanvasHeight - mazeHeightPx)/2;
    };

    this.redrawCurrentMaze = function redrawCurrentMaze()
    {
        this.drawMaze(currentMazeDefinition);
    };

    this.drawMaze = function drawMaze(mazeDefinition)
    {
        this.setMazeDefinition(mazeDefinition);

        var mazeCanvas = document.getElementById("maze-canvas-back");
        drawMazeToContext(mazeDefinition, mazeCanvas.getContext("2d"));

        var mazeCanvasOutput = document.getElementById("maze-canvas-front");
        drawMazeToContext(mazeDefinition, mazeCanvasOutput.getContext("2d"));
    };

    this.drawExplorerLocation = function drawExplorerLocation(oldLocation, newLocation)
    {
        var mazeCanvas = document.getElementById("maze-canvas-back");
        var ctx = mazeCanvas.getContext("2d");

        ctx.beginPath();
        ctx.strokeStyle = "#FF0000";
        ctx.moveTo(absoluteXCoordinatePx(oldLocation.x) + mazeSquareSize/2, absoluteYCoordinatePx(oldLocation.y) + mazeSquareSize/2);
        ctx.lineTo(absoluteXCoordinatePx(newLocation.x) + mazeSquareSize/2, absoluteYCoordinatePx(newLocation.y) + mazeSquareSize/2);
        ctx.stroke();

        var outputCanvas = document.getElementById("maze-canvas-front");
        var outputCtx = outputCanvas.getContext("2d");
        outputCtx.drawImage(mazeCanvas, 0, 0);
        outputCtx.drawImage(explorerSprite, absoluteXCoordinatePx(newLocation.x)+3, absoluteYCoordinatePx(newLocation.y));
    };

    this.loadSprites = function loadSprites()
    {
        explorerSprite = new Image();
        explorerSprite.src = "../images/explorer.png";

        wallSprite = new Image();
        wallSprite.src = "../images/wall.png";

        pathSprite = new Image();
        pathSprite.src = "../images/path.png";

        exitSprite = new Image();
        exitSprite.src = "../images/exit.png";

        keySprite = new Image();
        keySprite.src = "../images/key.png";

        keyFoundSprite = new Image();
        keyFoundSprite.src = "../images/tick.png";
    };

    this.keyFound = function keyFound(location)
    {
        var mazeCanvas = document.getElementById("maze-canvas-back");
        var ctx = mazeCanvas.getContext("2d");

        ctx.drawImage(keyFoundSprite, absoluteXCoordinatePx(location.x), absoluteYCoordinatePx(location.y));
    }
}



