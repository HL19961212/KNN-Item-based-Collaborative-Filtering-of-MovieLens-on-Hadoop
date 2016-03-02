package space.yixian.hadoop;
import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Mapper2 extends Mapper<Text, Text, Text, Text> {
	
	/**
	 * 输入 input：<userid, moive1-rate1 moive2-rate2 ... moiveN-rateN>
	 * 将某user的所有评分电影两两组合，便于reducer之后计算电影两两之间的距离
	 * Combine the rates of movies in pairs of one user in order to calculate the distance between these two movies  
	 * 输出 output：<moiveA-movieB,rateA-rateB>
	 */
	@Override
	protected void map(Text key, Text value, Mapper<Text, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {

		String[] movieRatePair = value.toString().split(" ");
		
		// moive1-rate1 moive2-rate2 ... moiveN-rateN   -->   <moiveA movieB,rateA rateB>
		for(int i = 0 ; i < movieRatePair.length; i++){
			for(int j = 0 ; j < movieRatePair.length; j++){		
				
				String[] movRateA= movieRatePair[i].split("-");
				String[] movRateB = movieRatePair[j].split("-");
				
				Integer movieA = Integer.valueOf(movRateA[0]); // get movieA id
				Integer movieB = Integer.valueOf(movRateB[0]);// get movieB id
				
				if(movieA < movieB){ //combine in pairs without replicate 两两组合无重复
					String rateA = movRateA[1];// get rateA
					String rateB = movRateB[1];// get rateB
					
					context.write(new Text(movieA.toString()+"-"+movieB.toString()), new Text(rateA+"-"+rateB));
				}
				
	
			}
		}
		
					
		
	}
}
