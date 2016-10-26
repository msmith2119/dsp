

import org.knowm.xchart.*


class PlotUtils { 



 static plotSingle(String title,String xlabel, String ylabel, String seriesName,double[] x, double[] y ) { 
         println "x="+x.length
	 println "y="+y.length
         println "title="+title
        XYChart chart = QuickChart.getChart(title, xlabel, ylabel, seriesName, x, y);
	    new SwingWrapper(chart).displayChart();  	       
}
}
