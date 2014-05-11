
function Graphics() { }

Graphics.prototype.mazeCanvasWidth = 1000;
Graphics.prototype.mazeCanvasHeight = 800;
Graphics.prototype.mazeSquareSize = 20;

Graphics.prototype.currentMazeDefinition = null;
Graphics.prototype.mazeMaxXCoordinate = 0;
Graphics.prototype.mazeMaxYCoordinate = 0;
Graphics.prototype.mazeXOffsetPx = 0;
Graphics.prototype.mazeYOffsetPx = 0;

Graphics.prototype.explorerSprite = null;
Graphics.prototype.wallSprite = null;
Graphics.prototype.pathSprite = null;
Graphics.prototype.exitSprite = null;

Graphics.prototype.drawMaze = function drawMaze(mazeDefinition)
{
    this.currentMazeDefinition = mazeDefinition;
    var mazeCanvas = document.getElementById("maze-canvas-back");
    var mazeCanvasOutput = document.getElementById("maze-canvas-front");

    //intialise canvas size
    mazeCanvas.width = this.mazeCanvasWidth;
    mazeCanvas.height = this.mazeCanvasHeight;
    mazeCanvasOutput.width = this.mazeCanvasWidth;
    mazeCanvasOutput.height = this.mazeCanvasHeight;

    this.mazeMaxXCoordinate = mazeDefinition.maxXCoordinate;
    this.mazeMaxYCoordinate = mazeDefinition.maxYCoordinate;
    var mazeWidthPx = this.mazeMaxXCoordinate * this.mazeSquareSize;
    var mazeHeightPx = this.mazeMaxYCoordinate * this.mazeSquareSize;
    this.mazeXOffsetPx = (this.mazeCanvasWidth - mazeWidthPx)/2;
    this.mazeYOffsetPx = (this.mazeCanvasHeight - mazeHeightPx)/2;

    this.drawMazeToContext(mazeDefinition, mazeCanvas.getContext("2d"));
    this.drawMazeToContext(mazeDefinition, mazeCanvasOutput.getContext("2d"));
};

//private?
Graphics.prototype.drawMazeToContext = function drawMazeToContext(mazeDefinition, ctx)
{
    ctx.clearRect(0, 0, this.mazeCanvasWidth, this.mazeCanvasHeight);

    //border
    ctx.beginPath();
    ctx.strokeStyle = "#000000";
    ctx.rect(0, 0, this.mazeCanvasWidth, this.mazeCanvasHeight);
    ctx.stroke();

    for(var y=0 ; y<=this.mazeMaxYCoordinate; y++)
    {
        for(var x=0 ; x<=this.mazeMaxXCoordinate ; x++)
        {
            if(mazeDefinition.grid[x][y])
            {
                ctx.drawImage(this.pathSprite, this.absoluteXCoordinatePx(x), this.absoluteYCoordinatePx(y));
            }
            else
            {
                ctx.drawImage(this.wallSprite, this.absoluteXCoordinatePx(x), this.absoluteYCoordinatePx(y));
            }
        }
    }

    //exit
    ctx.drawImage(this.exitSprite, this.absoluteXCoordinatePx(mazeDefinition.exit.x), this.absoluteYCoordinatePx(mazeDefinition.exit.y))
};

Graphics.prototype.redrawCurrentMaze = function redrawCurrentMaze()
{
    this.drawMaze(this.currentMazeDefinition);
};

Graphics.prototype.drawExplorerLocation = function drawExplorerLocation(oldLocation, newLocation)
{
    var mazeCanvas = document.getElementById("maze-canvas-back");
    var ctx = mazeCanvas.getContext("2d");

    ctx.beginPath();
    ctx.strokeStyle = "#FF0000";
    ctx.moveTo(this.absoluteXCoordinatePx(oldLocation.x) + this.mazeSquareSize/2, this.absoluteYCoordinatePx(oldLocation.y) + this.mazeSquareSize/2);
    ctx.lineTo(this.absoluteXCoordinatePx(newLocation.x) + this.mazeSquareSize/2, this.absoluteYCoordinatePx(newLocation.y) + this.mazeSquareSize/2);
    ctx.stroke();

    var outputCanvas = document.getElementById("maze-canvas-front");
    var outputCtx = outputCanvas.getContext("2d");
    outputCtx.drawImage(mazeCanvas, 0, 0);
    outputCtx.drawImage(this.explorerSprite, this.absoluteXCoordinatePx(newLocation.x)+3, this.absoluteYCoordinatePx(newLocation.y));
};

Graphics.prototype.absoluteXCoordinatePx = function absoluteXCoordinatePx(xGridPos)
{
    return this.mazeXOffsetPx + xGridPos*this.mazeSquareSize
};

Graphics.prototype.absoluteYCoordinatePx = function absoluteYCoordinatePx(yGridPos)
{
    //y origin is reversed from grid to canvas
    var yPosition = (this.mazeMaxYCoordinate-yGridPos);
    return this.mazeYOffsetPx + yPosition*this.mazeSquareSize
};

Graphics.prototype.loadSprites = function loadSprites()
{
    this.explorerSprite = new Image();
    this.explorerSprite.src = "../images/explorer.png";

    this.wallSprite = new Image();
    this.wallSprite.src = "../images/wall.png";

    this.pathSprite = new Image();
    this.pathSprite.src = "../images/path.png";

    this.exitSprite = new Image();
    this.exitSprite.src = "../images/exit.png";
};
