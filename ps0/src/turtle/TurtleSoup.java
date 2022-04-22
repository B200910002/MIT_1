/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package turtle;

import java.util.List;
import java.util.ArrayList;


public class TurtleSoup {
	public static int pi_degree = 180; // 180 degree is a pi
    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
    		turtle.color(PenColor.YELLOW);
    		int i = 0;
    		while(i < 4) {
    			turtle.forward(sideLength);
        		turtle.turn(90);
        		i++;
    		}
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
    	if(sides > 2)
    		return (double) pi_degree-(2.0*pi_degree / sides) ;
    	else
    		throw new RuntimeException("where sides must be > 2 !");
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        int ang = (int) Math.round(angle);
    	if(0 < ang && ang < pi_degree)
    		return (int) 2*pi_degree / (pi_degree-ang);
    	else
    		throw new RuntimeException("angle size of interior angles in degrees, where 0 < angle < 180 !");
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        try{
        	
        	double angle = calculateRegularPolygonAngle(sides);
        	int calculateSides = calculatePolygonSidesFromAngle(angle);
        	for(int i = 0; i < calculateSides; i++) {
        		turtle.forward(sideLength);
        		turtle.turn(pi_degree-angle);
        	}
        }catch(RuntimeException e){throw new RuntimeException("implement me!");}
    }

    /**
     * Given the current direction, current location, and a target location, calculate the heading
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentHeading. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentHeading current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to heading (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateHeadingToPoint(double currentHeading, int currentX, int currentY,
                                                 int targetX, int targetY) {
    		double angle = Math.atan2(targetX - currentX, targetY - currentY) * pi_degree / Math.PI;
    		double turnAngle = angle -currentHeading;
    		if(turnAngle < 0)
    			turnAngle += 2*pi_degree;
    		return turnAngle;
    }

    /**
     * Given a sequence of points, calculate the heading adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateHeadingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of heading adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateHeadings(List<Integer> xCoords, List<Integer> yCoords) {
    	int xListLength = xCoords.size();
    	int yListLength = yCoords.size();
    	List<Double> list = new ArrayList<>();
    	if(0==Integer.compare(xListLength, yListLength) && xListLength > 1) {
    		double currentHeading = 0;
    		for(int i=0;i<xListLength-1; i++) {
    			double adjustmentHeading = calculateHeadingToPoint(currentHeading,xCoords.get(i),yCoords.get(i),xCoords.get(i+1),yCoords.get(i+1));
    			list.add(adjustmentHeading);
    			currentHeading = adjustmentHeading - currentHeading;
    		}
    	}
    	return list;
    }

    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
    	//work of art
    	ArrayList<PenColor> colors = new ArrayList<PenColor>();
    	colors.add(PenColor.PINK);
    	colors.add(PenColor.YELLOW);
    	colors.add(PenColor.RED);
    	colors.add(PenColor.PINK);
    	colors.add(PenColor.YELLOW);
    	colors.add(PenColor.RED);
    	turtle.turn(-90);
    	for(int i=0;i<6;i++) {
    		turtle.color(colors.get(i));
    		for(int j=0;j<16;j++)
    			drawRegularPolygon(turtle,30,j);
    		turtle.turn(2*pi_degree/6);
    	}
	}
    	
//    	for(int i=0;i<36;i++) {
//    		drawSquare(turtle, 100);
//    		turtle.turn(10);
//    	}
    	
//    	for(int i=0;i<36;i++) {
//    		drawSquare(turtle, 10+i);
//    		turtle.turn(10);
//    	}
    	
    	
    	//heading point
//    	turtle.color(PenColor.PINK);
//    	int x[] = {0,100,100,200,200};
//    	int y[] = {0,0,100,100,100};
//    	
//    	ArrayList<Point> points = new ArrayList<Point>();
//    	for(int i=0;i<x.length;i++)
//    		points.add(new Point(x[i], y[i]));
//    	
//    	ArrayList<Integer> xCoords = new ArrayList<Integer>();
//    	for(Point point: points)
//    		xCoords.add((int)point.x());
//    	
//    	ArrayList<Integer> yCoords = new ArrayList<Integer>();
//    	for(Point point: points)
//    		yCoords.add((int)point.y());
//    	
//    	List<Double> heads =  calculateHeadings(xCoords, yCoords);
//    	for(int i=0;i<heads.size();i++) {
//    		turtle.turn(heads.get(i));
//    		turtle.forward(100);
//    	}
//    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();
//        drawSquare(turtle,100);
//        turtle.color(PenColor.YELLOW);
//        drawRegularPolygon(turtle, 9, 50);
        drawPersonalArt(turtle);        
        
        // draw the window
        turtle.draw();
    }

}
