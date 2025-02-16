import random
import tkinter as tk
from tkinter import font
from tkinter import messagebox

root = tk.Tk()
CELLSIZE = 25
# Global variables to hold the two game tables for players
table1, table2 = None, None

class Table:
    def __init__(self, player, position):
        self.player = player  # Identifies if the player is a human or a robot
        self.position = position  # Determines the position of the table in the UI
        self.planes = []  # Stores the positions of the planes
        self.table = tk.Frame(root, width=14*CELLSIZE, height=13*CELLSIZE)
        self.planeDirection = "down"  # Default plane direction
        self.doneAttack = True  # Flag to check if the player has completed their attack move

        # Position the game board in the interface
        if position == 1:
            self.table.grid(row=0, column=0)
        elif position == 2:
            self.table.grid(row=0, column=1, padx=100)

        # Label indicating which player owns the board
        playerDescription = tk.Label(self.table, text=f"Player {self.position}")
        playerDescription.grid(row=0, column=4, columnspan=3)

        # Create the 10x10 grid canvas for the player's board
        self.gridForThePlanes = tk.Canvas(self.table, width=10*CELLSIZE, height=10*CELLSIZE)
        self.gridForThePlanes.grid(row=2, column=1, rowspan=10, columnspan=10, pady=0)
        
        self.initPlayground()  # Initialize the board

        # Add column labels (A-J) above the grid
        columnLetterIndex = ord('A') - 1
        for i in range(1, 11):
            columnLetterIndexLabel = tk.Label(self.table, text=chr(columnLetterIndex + i))
            columnLetterIndexLabel.grid(row=12, column=i)

        # Set up buttons based on player type
        if player == "Person":
            self.stateButton = tk.Button(self.table, text="Select planes", command=self.generatePersonPlanesPlacement)
            self.stateButton.grid(row=14, column=4, columnspan=4, padx=10)
            self.ready = False  # Player must manually place their planes
        else:
            self.planes = self.generateRobotPlanesPlacement()
            self.ready = True  # AI places its planes automatically

    def initPlayground(self):
        """Initializes the grid with orange cells representing available positions."""
        self.playground = []  # Matrix to store plane positions
        self.graphicPlayground = []  # Stores graphical cell representations

        for rowIndex in range(0, 10):
            numericRow = []  # Stores plane placement data
            graphicRow = []  # Stores UI elements for the grid

            # Label the row numbers on the left side
            rowIndexLabel = tk.Label(self.table, text=f"{rowIndex + 1}")
            rowIndexLabel.grid(row=rowIndex + 2, column=0)

            for columnIndex in range(0, 10):
                numericRow.append(0)  # 0 means no plane part is present
                
                # Create a graphical representation of the grid cell
                graphicRow.append(self.gridForThePlanes.create_rectangle(
                    columnIndex * CELLSIZE,
                    rowIndex * CELLSIZE, 
                    (columnIndex + 1) * CELLSIZE,
                    (rowIndex + 1) * CELLSIZE,
                    fill="orange", outline="black"
                ))

            self.graphicPlayground.append(graphicRow)
            self.playground.append(numericRow)

    def changePlaneDirection(self, event):
        """Changes the plane direction when the mouse wheel is scrolled."""
        self.printRealPlayground() 
        
        # Get the clicked cell's position
        headPositionRow, headPositionColumn = event.y // CELLSIZE, event.x // CELLSIZE
        
        # Define the possible directions
        directions = {0: "down", 1: "right", 2: "up", 3: "left"}
        
        # Map current direction to an index
        if self.planeDirection == "down":
            direction = 0
        elif self.planeDirection == "right":
            direction = 1
        elif self.planeDirection == "up":
            direction = 2
        else:
            direction = 3
        
        # Adjust direction based on mouse wheel movement
        if event.delta > 0:
            direction += 1
        else:
            direction -= 1

        # Ensure direction stays within bounds (0-3)
        if direction > 3:
            direction -= 4
        if direction < 0:
            direction += 4

        # Update the plane's direction
        self.planeDirection = directions[direction]
        
        # Show the imaginary plane placement after rotation
        self.printImaginaryPlane(headPositionRow, headPositionColumn)


    def printImaginaryPlaneDown(self,headRow,headColumn):
        """
        Draws an imaginary plane on the grid when the direction is 'down'.
        Marks the affected cells in gray.
        """
        if(headRow+1<10):
            self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow+1][headColumn], fill="gray")
            if(headColumn+1<10):
                self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow+1][headColumn+1], fill="gray")
                if(headColumn+2<10):
                    self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow+1][headColumn+2], fill="gray")
            if(headColumn-1>=0):
                self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow+1][headColumn-1], fill="gray")
                if(headColumn-2>=0):
                    self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow+1][headColumn-2], fill="gray")
        if(headRow+2<10):
            self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow+2][headColumn], fill="gray")
        if(headRow+3<10):
            self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow+3][headColumn], fill="gray")
            if(headColumn+1<10):
                self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow+3][headColumn+1], fill="gray")
            if(headColumn-1>=0):
                self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow+3][headColumn-1], fill="gray")

        
    def printImaginaryPlaneRight(self,headRow,headColumn):
        """
        Draws an imaginary plane on the grid when the direction is 'right'.
        Marks the affected cells in gray.
        """
        if(headColumn+1<10):
            self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow][headColumn+1], fill="gray")
            if(headRow-1>=0):
                self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow-1][headColumn+1], fill="gray")
                if(headRow-2>=0):
                    self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow-2][headColumn+1], fill="gray")
            if(headRow+1<10):
                self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow+1][headColumn+1], fill="gray")
                if(headRow+2<10):
                    self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow+2][headColumn+1], fill="gray")
        if(headColumn+2<10):
            self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow][headColumn+2], fill="gray")
        if(headColumn+3<10):
            self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow][headColumn+3], fill="gray")
            if(headRow-1>=0):
                self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow-1][headColumn+3], fill="gray")
            if(headRow+1<10):
                self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow+1][headColumn+3], fill="gray")


    def printImaginaryPlaneUp(self,headRow,headColumn):
        """
        Draws an imaginary plane on the grid when the direction is 'up'.
        Marks the affected cells in gray.
        """
        if(headRow-1>=0):
            self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow-1][headColumn], fill="gray")
            if(headColumn+1<10):
                self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow-1][headColumn+1], fill="gray")
                if(headColumn+2<10):
                    self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow-1][headColumn+2], fill="gray")
            if(headColumn-1>=0):
                self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow-1][headColumn-1], fill="gray")
                if(headColumn-2>=0):
                    self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow-1][headColumn-2], fill="gray")
        if(headRow-2>=0):
            self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow-2][headColumn], fill="gray")
        if(headRow-3>=0):
            self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow-3][headColumn], fill="gray")
            if(headColumn+1<10):
                self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow-3][headColumn+1], fill="gray")
            if(headColumn-1>=0):
                self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow-3][headColumn-1], fill="gray")


    def printImaginaryPlaneLeft(self,headRow,headColumn):
        """
        Highlights the imaginary plane extending to the left from the given head position.
        The plane consists of a central column and two adjacent columns forming a T-like shape.
        """
        if(headColumn-1>=0):
            self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow][headColumn-1], fill="gray")
            if(headRow-1>=0):
                self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow-1][headColumn-1], fill="gray")
                if(headRow-2>=0):
                    self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow-2][headColumn-1], fill="gray")
            if(headRow+1<10):
                self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow+1][headColumn-1], fill="gray")
                if(headRow+2<10):
                    self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow+2][headColumn-1], fill="gray")
        if(headColumn-2>=0):
            self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow][headColumn-2], fill="gray")
        if(headColumn-3>=0):
            self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow][headColumn-3], fill="gray")
            if(headRow+1<10):
                self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow+1][headColumn-3], fill="gray")
            if(headRow-1>=0):
                self.gridForThePlanes.itemconfig(self.graphicPlayground[headRow-1][headColumn-3], fill="gray")



    def printImaginaryPlane(self,row,column):
        """
        Highlights the entire imaginary plane based on the selected direction.
        The plane head is marked in red, and the rest of the plane is filled with gray.
        """
        self.gridForThePlanes.itemconfig(self.graphicPlayground[row][column], fill="red")

        if(self.planeDirection == "down"):
            self.printImaginaryPlaneDown(row,column)
        elif(self.planeDirection == "right"):
            self.printImaginaryPlaneRight(row,column)
        elif(self.planeDirection == "up"):
            self.printImaginaryPlaneUp(row,column)
        elif(self.planeDirection == "left"):
            self.printImaginaryPlaneLeft(row,column)


    def generatePersonPlanesPlacement(self):
        """
        Handles the plane placement phase for the player.
        - When activated, the player can place planes by clicking on the grid.
        - The function binds mouse events for selection and rotation.
        - When deactivated, it hides the planes and removes event bindings.
        """
        if(self.stateButton["text"] == "Select planes"):

            self.stateButton.config(text = "Hide planes")
            self.printRealPlayground()
            messagebox.showinfo("Info","Now, the other player should not watch while you place the planes")

            # Bind mouse click for placing planes and scroll wheel for changing direction
            self.gridForThePlanes.bind("<Button-1>",lambda e: self.verifyPossibilityPerson(e))
            self.gridForThePlanes.bind("<MouseWheel>",lambda e: self.changePlaneDirection(e))

            # Bind hover effects to show and hide imaginary planes
            for i in range(0,10):
                for j in range(0,10):
                    self.gridForThePlanes.tag_bind(self.graphicPlayground[i][j], "<Enter>", lambda e,i=i,j=j: self.printImaginaryPlane(i,j))
                    self.gridForThePlanes.tag_bind(self.graphicPlayground[i][j], "<Leave>", lambda e: self.printRealPlayground())

        elif(self.stateButton["text"] == "Hide planes"):

            # Unbind events to disable plane selection
            self.gridForThePlanes.unbind("<Button-1>")
            self.stateButton.config(text = "Select planes")
            self.gridForThePlanes.unbind("<MouseWheel>")
            for i in range(0,10):
                for j in range(0,10):
                    self.gridForThePlanes.tag_unbind(self.graphicPlayground[i][j], "<Enter>")
                    self.gridForThePlanes.tag_unbind(self.graphicPlayground[i][j], "<Leave>")
            self.printHiddenPlayground()
            

    def printAttackedPlayGround(self):
        """
        Updates the game board based on attack results.
        The grid colors are assigned based on hit or miss values in the 'playground' matrix.
        """
        colors = {2:"orange",1:"orange",0:"orange",-1:"gray",-2:"white",-3:"magenta"}
        for i in range(0,10):
            for j in range(0,10):
                self.gridForThePlanes.itemconfig(self.graphicPlayground[i][j],fill=colors[self.playground[i][j]])

    
    def printRealPlayground(self):
        """
        Displays the actual plane positions during the selection phase.  
        The grid colors indicate different statuses:  
        - Magenta (-3): Successfully hit the head of a plane  
        - White (-2): Successfully hit a part of a plane  
        - Gray (-1): Missed shot (empty area)  
        - Orange (0): Untouched grid space  
        - White (1): Untouched part of a plane's body  
        - Magenta (2): Untouched plane's head  
        """
        colors = {-3:"magenta", -2:"white", -1:"gray", 0:"orange", 1:"white", 2:"magenta"}

        for i in range(0,10):
            for j in range(0,10):
                self.gridForThePlanes.itemconfig(self.graphicPlayground[i][j],fill=colors[self.playground[i][j]])


    def printHiddenPlayground(self):
        """
        Displays a hidden version of the board where all tiles appear orange.
        This is used when the player should not see the exact placement of planes.
        """
        for i in range(0,10):
            for j in range(0,10):
                self.gridForThePlanes.itemconfig(self.graphicPlayground[i][j],fill="orange")


    def generateRobotPlanesPlacement(self):
        planes = []  # List to store the placed planes
        while len(planes) < 3:  # The robot should place 3 planes
            headPositionRow = random.randint(0,9)  # Random row for the plane's head
            headPositionColumn = random.randint(0,9)  # Random column for the plane's head
            choices = self.verifyPossibilityRobot(headPositionRow, headPositionColumn)  # Get possible directions
            
            if len(choices) != 0:  # If there are valid directions
                direction = random.choice(choices)  # Randomly pick a valid direction
                # Place the plane based on the chosen direction
                if direction == 'down':
                    self.createPlaneDown(headPositionRow, headPositionColumn)
                elif direction == 'up':
                    self.createPlaneUp(headPositionRow, headPositionColumn)
                elif direction == 'left':
                    self.createPlaneLeft(headPositionRow, headPositionColumn)
                elif direction == 'right':
                    self.createPlaneRight(headPositionRow, headPositionColumn)
                else:
                    print("something went wrong in generateRobotPlanesPlacement function")  # Debugging message
                # Store the plane details
                planes.append({"headPositionRow": headPositionRow, "headPositionColumn": headPositionColumn, "direction": direction})
        return planes  # Return the list of placed planes


    def createPlaneDown(self,row,column):
        # Places a plane that is oriented downward
        self.playground[row][column] = 2
        self.playground[row+1][column] = 1
        self.playground[row+1][column+1] = 1 
        self.playground[row+1][column+2] = 1
        self.playground[row+1][column-2] = 1
        self.playground[row+1][column-1] = 1
        self.playground[row+2][column] = 1
        self.playground[row+3][column] = 1
        self.playground[row+3][column+1] = 1
        self.playground[row+3][column-1] = 1

    def createPlaneUp(self,row,column):
        # Places a plane that is oriented upward
        self.playground[row][column] = 2
        self.playground[row-1][column] = 1
        self.playground[row-1][column+1] = 1
        self.playground[row-1][column+2] = 1
        self.playground[row-1][column-2] = 1
        self.playground[row-1][column-1] = 1
        self.playground[row-2][column] = 1
        self.playground[row-3][column] = 1
        self.playground[row-3][column+1] = 1
        self.playground[row-3][column-1] = 1
    
    def createPlaneLeft(self,row,column):
        # Places a plane that is oriented leftward
        self.playground[row][column] = 2
        self.playground[row][column-1] = 1
        self.playground[row+1][column-1] = 1
        self.playground[row+2][column-1] = 1
        self.playground[row-2][column-1] = 1
        self.playground[row-1][column-1] = 1
        self.playground[row][column-2] = 1
        self.playground[row][column-3] = 1
        self.playground[row+1][column-3] = 1
        self.playground[row-1][column-3] = 1

    def createPlaneRight(self,row,column):
        # Places a plane that is oriented rightward
        self.playground[row][column] = 2
        self.playground[row][column+1] = 1
        self.playground[row+1][column+1] = 1
        self.playground[row+2][column+1] = 1
        self.playground[row-2][column+1] = 1
        self.playground[row-1][column+1] = 1
        self.playground[row][column+2] = 1
        self.playground[row][column+3] = 1
        self.playground[row+1][column+3] = 1
        self.playground[row-1][column+3] = 1

    def verifyPossibilityDown(self,row,column):
        # Ensure the plane does not exceed grid boundaries and does not overlap another plane
        if (row + 3 <= 9 and
            column + 2 <= 9 and
            column - 2 >= 0 and
            self.playground[row+1][column] == 0 and
            self.playground[row+1][column+1] == 0 and
            self.playground[row+1][column+2] == 0 and
            self.playground[row+1][column-2] == 0 and
            self.playground[row+1][column-1] == 0 and
            self.playground[row+2][column] == 0 and
            self.playground[row+3][column] == 0 and
            self.playground[row+3][column+1] == 0 and
            self.playground[row+3][column-1] == 0 
            ):
            return True
        else:
            return False
    
    def verifyPossibilityUp(self,row,column):
        # Ensure the plane does not exceed grid boundaries and does not overlap another plane
        if (row - 3 >= 0 and
            column + 2 <= 9 and
            column - 2 >= 0 and
            self.playground[row-1][column] == 0 and
            self.playground[row-1][column+1] == 0 and
            self.playground[row-1][column+2] == 0 and
            self.playground[row-1][column-2] == 0 and
            self.playground[row-1][column-1] == 0 and
            self.playground[row-2][column] == 0 and
            self.playground[row-3][column] == 0 and
            self.playground[row-3][column+1] == 0 and
            self.playground[row-3][column-1] == 0 
            ):
            return True
        else:
            return False

    def verifyPossibilityLeft(self,row,column):
        # Ensure the plane does not exceed grid boundaries and does not overlap another plane
        if (column - 3 >= 0 and
            row + 2 <= 9 and
            row - 2 >= 0 and
            self.playground[row][column-1] == 0 and
            self.playground[row+1][column-1] == 0 and
            self.playground[row+2][column-1] == 0 and
            self.playground[row-2][column-1] == 0 and
            self.playground[row-1][column-1] == 0 and
            self.playground[row][column-2] == 0 and
            self.playground[row][column-3] == 0 and
            self.playground[row+1][column-3] == 0 and
            self.playground[row-1][column-3] == 0 
            ):
            return True
        else:
            return False

    def verifyPossibilityRight(self,row,column):
        # Ensure the plane does not exceed grid boundaries and does not overlap another plane
        if (column + 3 <= 9 and
            row + 2 <= 9 and
            row - 2 >= 0 and
            self.playground[row][column+1] == 0 and
            self.playground[row+1][column+1] == 0 and
            self.playground[row+2][column+1] == 0 and
            self.playground[row-2][column+1] == 0 and
            self.playground[row-1][column+1] == 0 and
            self.playground[row][column+2] == 0 and
            self.playground[row][column+3] == 0 and
            self.playground[row+1][column+3] == 0 and
            self.playground[row-1][column+3] == 0 
            ):
            return True
        else:
            return False

    def verifyPossibilityRobot(self, row, column):
        choices = []  # List to store valid placement directions
        
        # If the cell is already occupied by a plane, return an empty list
        if self.playground[row][column] != 0:
            return choices

        # Check all possible directions and add them to the choices list if valid
        if self.verifyPossibilityDown(row, column):
            choices.append('down')
        if self.verifyPossibilityUp(row, column):
            choices.append('up')
        if self.verifyPossibilityLeft(row, column):
            choices.append('left')
        if self.verifyPossibilityRight(row, column):
            choices.append('right')

        return choices  # Return the list of possible directions

    

    def verifyPossibilityPerson(self, event):
        # Calculate the row and column where the player clicked
        headPositionRow, headPositionColumn = event.y // CELLSIZE, event.x // CELLSIZE
        
        ok = False  # Flag to check if placement is possible

        # Check placement validity based on the chosen direction
        if self.planeDirection == "up":
            ok = self.verifyPossibilityUp(headPositionRow, headPositionColumn)
        elif self.planeDirection == "down":
            ok = self.verifyPossibilityDown(headPositionRow, headPositionColumn)
        elif self.planeDirection == "right":
            ok = self.verifyPossibilityRight(headPositionRow, headPositionColumn)
        elif self.planeDirection == "left":
            ok = self.verifyPossibilityLeft(headPositionRow, headPositionColumn)

        if ok:  # If the placement is valid
            # Place the plane in the selected direction
            if self.planeDirection == "up":
                self.createPlaneUp(headPositionRow, headPositionColumn)
            elif self.planeDirection == "down":
                self.createPlaneDown(headPositionRow, headPositionColumn)
            elif self.planeDirection == "right":
                self.createPlaneRight(headPositionRow, headPositionColumn)
            elif self.planeDirection == "left":
                self.createPlaneLeft(headPositionRow, headPositionColumn)
            else:
                messagebox.showerror("Fatal Error", "you can't be here, right?")  # Debugging message
            
            # Store the plane's details
            self.planes.append({"headPositionRow": headPositionRow, "headPositionColumn": headPositionColumn, "direction": self.planeDirection})

            if len(self.planes) == 3:  # If all 3 planes are placed
                self.gridForThePlanes.unbind("<Button-1>")  # Disable further placement
                self.stateButton.destroy()  # Remove the state button

                # Unbind hover events to prevent further interactions
                for i in range(10):
                    for j in range(10):
                        self.gridForThePlanes.tag_unbind(self.graphicPlayground[i][j], "<Enter>")
                        self.gridForThePlanes.tag_unbind(self.graphicPlayground[i][j], "<Leave>")
                
                self.printHiddenPlayground()  # Update the board for hidden mode
                messagebox.showinfo("Info", "Planes position saved successfully")  # Notify the player
                self.ready = True  # Mark player as ready

            else:
                self.printRealPlayground()  # Update the board to show current placements

        else:
            messagebox.showerror("Error", "You can't place a plane here")  # Notify the player of invalid placement



    def eliminatePlaneUp(self,row,column):
        # Removes a plane that is oriented upward
        self.playground[row][column] = -3
        self.playground[row-1][column] = -2
        self.playground[row-1][column+1] = -2
        self.playground[row-1][column+2] = -2
        self.playground[row-1][column-2] = -2
        self.playground[row-1][column-1] = -2
        self.playground[row-2][column] = -2
        self.playground[row-3][column] = -2
        self.playground[row-3][column+1] = -2
        self.playground[row-3][column-1] = -2


    def eliminatePlaneLeft(self,row,column):
        # Removes a plane that is oriented leftward
        self.playground[row][column] = -3
        self.playground[row][column-1] = -2
        self.playground[row+1][column-1] = -2
        self.playground[row+2][column-1] = -2
        self.playground[row-2][column-1] = -2
        self.playground[row-1][column-1] = -2
        self.playground[row][column-2] = -2
        self.playground[row][column-3] = -2
        self.playground[row+1][column-3] = -2
        self.playground[row-1][column-3] = -2


    def eliminatePlaneDown(self,row,column):
        # Removes a plane that is oriented downward
        self.playground[row][column] = -3
        self.playground[row+1][column] = -2
        self.playground[row+1][column+1] = -2
        self.playground[row+1][column+2] = -2
        self.playground[row+1][column-2] = -2
        self.playground[row+1][column-1] = -2
        self.playground[row+2][column] = -2
        self.playground[row+3][column] = -2
        self.playground[row+3][column+1] = -2
        self.playground[row+3][column-1] = -2


    def eliminatePlaneRight(self,row,column):
        # Removes a plane that is oriented rightward
        self.playground[row][column] = -3
        self.playground[row][column+1] = -2
        self.playground[row+1][column+1] = -2
        self.playground[row+2][column+1] = -2
        self.playground[row-2][column+1] = -2
        self.playground[row-1][column+1] = -2
        self.playground[row][column+2] = -2
        self.playground[row][column+3] = -2
        self.playground[row+1][column+3] = -2
        self.playground[row-1][column+3] = -2


    def eliminatePlane(self,headRow,headColumn):
        """
        Removes a plane from the game board if its head position matches the given coordinates.
        Updates the board accordingly and removes the plane from the list.
        """
        for plane in self.planes:
            if(plane["headPositionRow"] == headRow and plane["headPositionColumn"] == headColumn):
                if(plane["direction"] == "up"):
                    self.eliminatePlaneUp(headRow,headColumn)
                elif(plane["direction"] == "left"):
                    self.eliminatePlaneLeft(headRow,headColumn)
                elif(plane["direction"] == "down"):
                    self.eliminatePlaneDown(headRow,headColumn)
                elif(plane["direction"] == "right"):
                    self.eliminatePlaneRight(headRow,headColumn)
                else:
                    print("Imposible case, u can't be here, right???")
                self.planes.remove(plane)
        if(len(self.planes)==0):
            return True
        else:
            return False


    def getAttackByPerson(self, event, infoMessage):
        #Handles an attack initiated by a player. Determines the outcome based on the attacked cell's state.
        y, x = event.y // CELLSIZE, event.x // CELLSIZE  # Convert pixel coordinates to grid indices
        ok = False
        position = 1

        # Check the state of the attacked cell
        if self.playground[y][x] == 2:
            ok = self.eliminatePlane(y, x)  # The head of a plane was hit
        elif self.playground[y][x] == 1:
            self.playground[y][x] = -2  # A part of a plane was hit
        elif self.playground[y][x] == 0:
            self.playground[y][x] = -1  # Missed attack
        else:
            messagebox.showerror("Error", "You already attacked this square, you missed your turn")
        
        # Disable further attacks until the next turn
        self.gridForThePlanes.unbind("<Button-1>")
        for i in range(10):
            for j in range(10):
                self.gridForThePlanes.tag_unbind(self.graphicPlayground[i][j], "<Enter>")
                self.gridForThePlanes.tag_unbind(self.graphicPlayground[i][j], "<Leave>")
        
        # Check if the game is over
        if ok:
            if self.position == 1:
                position = 2
            infoMessage.config(text=f"Player {position} won")
            finishGame()
            # Remove the "Next Turn" button
            for widget in root.winfo_children():
                if isinstance(widget, tk.Button) and widget.cget("text") == "Next Turn":
                    widget.destroy()
        else:
            infoMessage.config(text="Press Next Turn for the next turn")

        # Update the grid to reflect attacked positions
        self.printAttackedPlayGround()
        self.doneAttack = True


    def hoverCell(self,i,j):
        # Changes the color of a grid cell to light blue when hovered over.
        self.gridForThePlanes.itemconfig(self.graphicPlayground[i][j],fill="lightblue")

    
    def unHoverCell(self,i,j):
        # Restores the original color of a grid cell when the mouse leaves it.
        colors = {-3:"magenta",-2:"white",-1:"gray", 0:"orange",1:"orange",2:"orange"}
        self.gridForThePlanes.itemconfig(self.graphicPlayground[i][j],fill = colors[self.playground[i][j]])


    def getAttackablePlaneZones(self):
        # Retrieves a list of all grid coordinates where a plane (or its head) is present and can be attacked
        attackablePlaneZones = []
        for i in range(0,10):
            for j in range(0,10):
                if(self.playground[i][j] == 1 or self.playground[i][j] == 2):
                    attackablePlaneZones.append((i,j))
        return attackablePlaneZones


    def getNonAttackablePlaneZones(self):
        # Retrieves a list of all grid coordinates where no plane is present
        nonAttackablePlaneZones = []
        for i in range(0,10):
            for j in range(0,10):
                if(self.playground[i][j] == 0):
                    nonAttackablePlaneZones.append((i,j))
        return nonAttackablePlaneZones


    def robotAttackAPlane(self):
        """
        Simulates an attack by the robot. The robot randomly selects an attackable plane zone.
        If it hits a plane's head, the plane is eliminated; otherwise, the attack marks the cell as a hit.
        """
        ok = False
        attackablePlaneZones = self.getAttackablePlaneZones()  # Get possible attack positions
        planeZoneCoordinates = random.choice(attackablePlaneZones)  # Choose a random attack position

        # Check if the robot hit the head of a plane
        if self.playground[planeZoneCoordinates[0]][planeZoneCoordinates[1]] == 2:
            ok = self.eliminatePlane(planeZoneCoordinates[0], planeZoneCoordinates[1])
        else:
            self.playground[planeZoneCoordinates[0]][planeZoneCoordinates[1]] = -2  # Mark as a hit (not a head)

        return ok


    def getAttackByRobot(self, infoMessage):
        # Randomly decide if the robot hits (1) or misses (0)
        hit = random.randint(0, 1)
        ok = False
        position = 1

        if hit == 1:
            # If the robot hits, it attempts to attack a plane
            ok = self.robotAttackAPlane()
        else:
            # If the robot misses, it attacks a non-plane zone
            nonAttackablePlaneZones = self.getNonAttackablePlaneZones()
            if len(nonAttackablePlaneZones) == 0:
                # If no non-plane zones are left, it attacks a plane instead
                ok = self.robotAttackAPlane()
            else:
                # Choose a random non-plane zone and mark it as a miss (-1)
                nonPlaneZoneCoordinates = random.choice(nonAttackablePlaneZones)
                self.playground[nonPlaneZoneCoordinates[0]][nonPlaneZoneCoordinates[1]] = -1

        if ok:
            # If all 3 planes was eliminated, announce the winner and end the game
            if self.position == 1:
                position = 2
            infoMessage.config(text=f"Player {position} won")
            finishGame()
            # Remove the "Next Turn" button after the game ends
            for widget in root.winfo_children():
                if isinstance(widget, tk.Button) and widget.cget("text") == "Next Turn":
                    widget.destroy()
        else:
            # Otherwise, update the message to indicate the next turn
            infoMessage.config(text="Press Next Turn for your turn")
        
        # Print the updated playground state
        self.printAttackedPlayGround()


    def updatePlayGroundForAttack(self, infoMessage):
        # Print the current state of the attacked playground
        self.printAttackedPlayGround()
        # Bind the click event to register an attack by a human player
        self.gridForThePlanes.bind("<Button-1>", lambda e: self.getAttackByPerson(e, infoMessage))
        # Bind hover effects to highlight cells on mouseover
        for i in range(10):
            for j in range(10):
                self.gridForThePlanes.tag_bind(self.graphicPlayground[i][j], "<Enter>", lambda e, i=i, j=j: self.hoverCell(i, j))
                self.gridForThePlanes.tag_bind(self.graphicPlayground[i][j], "<Leave>", lambda e, i=i, j=j: self.unHoverCell(i, j))



def finishGame():
    # Print the real playgrounds of both tables with the planes positions when the game ends
    table1.printRealPlayground()
    table2.printRealPlayground()


def isWorthPlaying(opponent1, opponent2):
    # Check if the selected opponents make sense for the game

    if opponent1 == "Robot" and opponent2 == "Robot":
        # Prevent a match between two robots
        messagebox.showerror("Error", "Why would you want to watch 2 robots play?")
        return False

    if opponent1 == "Player 1":
        # Ensure Player 1 is correctly selected
        messagebox.showerror("Error", "Please select Player 1")
        return False

    if opponent2 == "Player 2":
        # Ensure Player 2 is correctly selected
        messagebox.showerror("Error", "Please select Player 2")
        return False
    
    return True


def nextTurn(table1, table2, infoMessage, turn):
    # Check if both players are ready
    if table1.ready and table2.ready:
        # Ensure both players have completed their attacks before switching turns
        if table1.doneAttack and table2.doneAttack:
            if turn[0] == 1:
                # Player 1's turn
                infoMessage.config(text="Player 1 turn")
                if table1.player == "Person":
                    table2.doneAttack = False
                    table2.updatePlayGroundForAttack(infoMessage)
                else:
                    table2.printAttackedPlayGround()
                    table2.getAttackByRobot(infoMessage)
                turn[0] = 2  # Switch to Player 2's turn
            else:
                # Player 2's turn
                infoMessage.config(text="Player 2 turn")
                if table2.player == "Person":
                    table1.doneAttack = False
                    table1.updatePlayGroundForAttack(infoMessage)
                else:
                    table1.printAttackedPlayGround()
                    table1.getAttackByRobot(infoMessage)
                turn[0] = 1  # Switch to Player 1's turn
        else:
            # Show an error if a player has not attacked yet
            messagebox.showerror("Error", "Attack a square first")
    else:
        # Warn the user if plane positions have not been selected
        messagebox.showwarning("Warning", "Select the position of the planes for all players")


def prepareGame(opponent1, opponent2):
    # Prepare the game by resetting the interface and initializing the tables
    global root
    for widget in root.winfo_children():
        widget.destroy()

    global table1, table2
    table1 = Table(opponent1, 1)
    table2 = Table(opponent2, 2)

    # Randomly determine which player starts
    turn = [random.randint(1, 2)]

    # Display an info message
    infoMessage = tk.Label(root, text="Please select planes and press Next Turn", font=("Arial", 20))
    infoMessage.grid(row=1, column=0, columnspan=2, pady=10)

    # Create the "Next Turn" button to advance the game
    nextTurnButton = tk.Button(root, text="Next Turn", width=25, height=5, command=lambda: nextTurn(table1, table2, infoMessage, turn))
    nextTurnButton.grid(row=2, column=0, columnspan=2, pady=10)

    # Create the "Return to Menu" button to exit the game setup
    exitButton = tk.Button(root, text="Return to Menu", width=25, height=5, command=showMenu)
    exitButton.grid(row=3, column=0, columnspan=2, pady=10)


def verifyGame(opponent1, opponent2):
    # Check if the game setup is valid before starting
    if(isWorthPlaying(opponent1,opponent2)):
        prepareGame(opponent1,opponent2)


def selectPlayers():
    # Clear the current UI by destroying all existing widgets
    global root
    for widget in root.winfo_children():
        widget.destroy()
    
    # Create StringVars to store the selected player types
    player1_var = tk.StringVar()
    player1_var.set("Player 1")  # Default value for Player 1

    player2_var = tk.StringVar()
    player2_var.set("Player 2")  # Default value for Player 2

    # Create a dropdown menu for Player 1 selection
    player1_menu = tk.Menubutton(root, textvariable=player1_var, width=50, height=10, relief=tk.RAISED)
    player1_menu.menu = tk.Menu(player1_menu, tearoff=0, font=font.Font(size=40))
    player1_menu["menu"] = player1_menu.menu

    # Add options for Player 1: Human or Robot
    player1_menu.menu.add_command(label="Person", command=lambda: player1_var.set("Person"))
    player1_menu.menu.add_command(label="Robot", command=lambda: player1_var.set("Robot"))

    player1_menu.pack()

    # Create a dropdown menu for Player 2 selection
    player2_menu = tk.Menubutton(root, textvariable=player2_var, width=50, height=10, relief=tk.RAISED)
    player2_menu.menu = tk.Menu(player2_menu, tearoff=0, font=font.Font(size=40))
    player2_menu["menu"] = player2_menu.menu

    # Add options for Player 2: Human or Robot
    player2_menu.menu.add_command(label="Person", command=lambda: player2_var.set("Person"))
    player2_menu.menu.add_command(label="Robot", command=lambda: player2_var.set("Robot"))

    player2_menu.pack()

    # Create a "Play" button that starts the game with the selected players
    play_button = tk.Button(root, text="Play", width=50, height=10, command=lambda: verifyGame(player1_var.get(), player2_var.get()))
    play_button.pack()


def showMenu():
    # Clear the current UI and display the main menu
    global root
    for widget in root.winfo_children():
        widget.destroy()

    # Create a "Play" button that leads to player selection
    play_button = tk.Button(root, text="Play", width=50, height=10, command=lambda: selectPlayers())
    play_button.pack(pady=10)

    # Create an "Exit" button to close the application
    exit_button = tk.Button(root, text="Exit", width=50, height=10, command=root.quit)
    exit_button.pack(pady=10)


def game():
    # Initialize the main game window
    global root
    root.title("Planes")  # Set the window title
    root.geometry("800x600")  # Set the window size

    showMenu()  # Display the main menu

    root.mainloop()  # Start the Tkinter main event loop


def main():
    # Start the game application
    game()


# Run the game if the script is executed directly
if __name__ == '__main__':
    main()