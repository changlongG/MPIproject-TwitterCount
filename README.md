# Project Description
This project implement a simple, parallelized application leveraging the University of Melbourne HPC facility SPARTAN. This application will search a large geocoded Twitter dataset to
identify tweet hotspots around Melbourne. The `melbGrid.json` file includes the latitudes and longitudes of a range of gridded boxes as illustrated in the figure
below, i.e. the latitude and longitude of each of the corners of the boxes is given in the file. The task is to search the large Twitter data set `bigTwitter.json` to identify Twitter activity
around Melbourne. Specifically you should:
 - Order (rank) the Grid boxes based on the total number of tweets made in each box and return the total count of tweets in each box;
 - Order (rank) the rows based on the total number of tweets in each row;
 - Order (rank) the columns based on the total number of tweets in each column.

# Implementation Steps
1. Implement MPJ Express interface to make the task parallel. MPJ Express is an open source Java message passing library that allows application developers to write and execute parallel applications for multicore processors and compute clusters/clouds.
2. Use `JSONArray` and `JSONObject` to parse json files.
3. Maintain an array for each parallel process to count the number of Twitters of each areas or boxes, the size of array is the number of distinct areas. Use `MPI.COMM_WORLD.Reduce()` method to combine results of all parallel processes.
   
# Useful notes
An individual tweet can be considered to occur in the box if its geo-location information (the tweet latitude and longitude given by the tweet coordinates) is within the box identified by the set of coordinates in melbGrid.json. It should be noted that the file bigTwitter.json includes many tweets that are not in this grid, e.g. they are from other Australian cities or from other parts of Victoria. 
