import au.com.bytecode.opencsv.CSVWriter;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Chanuka on 9/5/14 AD.
 */
public class NLPTest {

    public static void main(String[] args) throws IOException {

        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setDebugEnabled(true);
        configurationBuilder.setOAuthConsumerKey("XXXXXXX");
        configurationBuilder.setOAuthConsumerSecret("XXXXXX");
        configurationBuilder.setOAuthAccessToken("XXXXXX");
        configurationBuilder.setOAuthAccessTokenSecret("XXXXXXXX");

        TwitterStream twitterStream = new TwitterStreamFactory(configurationBuilder.build()).getInstance();

        StatusListener listener = new StatusListener() {

            @Override
            public void onException(Exception arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrubGeo(long arg0, long arg1) {
                // TODO Auto-generated method stub

            }
            @Override
            public void onStallWarning(StallWarning stallWarning){

            }
            List<String[]> data = new ArrayList<String[]>();

            String csv = "NLPTestoutput2.csv";
            CSVWriter writer = new CSVWriter(new FileWriter(csv));

            @Override
            public void onStatus(Status status) {
                User user = status.getUser();
                  // gets Username
                String username = status.getUser().getScreenName();

                status.isTruncated();
                String text=status.getText();
                if(!status.isTruncated() && !status.isRetweeted()){
                    Pattern pt = Pattern.compile("");
                    Matcher match = pt.matcher(text);
                    while (match.find()) {
                        String s = match.group();
                        text = text.replaceAll("\\" + s, " ");
                    }

                //String text = status.getText().replace("\n", "").replace("\r", "");
                System.out.println(status.getUser().getName()+"\n"+text);
                data.add(new String[]{status.getUser().getName(), text});

                }


                if(data.size()==100){
                    System.out.println("================Writing to File====================");
                    writer.writeAll(data);
                    data.clear();

                }

            }


            @Override
            public void onTrackLimitationNotice(int arg0) {
                // TODO Auto-generated method stub

            }

        };
        FilterQuery filterQuery = new FilterQuery();
        filterQuery.language(new String[]{"en"});
        String keywords[] = {"ebola"};
        filterQuery.track(keywords);
        twitterStream.addListener(listener);
        twitterStream.filter(filterQuery);



    }


}
