package tech.codingclub.helix.entity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import tech.codingclub.helix.global.HttpURLConnectionExample;

public class WikipediaDownloader {
    private String keyword;

    public WikipediaDownloader(){
    }
    public WikipediaDownloader(String keyword){
        this.keyword=keyword;
    }
    public WikiResult getResult() {
        //1.get clean keyword!
        //2.get the url for wikipedia
        //3.Make a GET request to Wikipedia!
        //4.parsing the useful result using jsoup
        //5.showing result!
        if (this.keyword == null || this.keyword.length() == 0) {
            return null;
        }
        //step-1
        this.keyword = this.keyword.trim().replaceAll("[ ]+", "_");
        //step-2
        String wikiUrl = getWikipediaForQuery(this.keyword);
        String  imageurl = null;
        String responce = "";

        try {
            //step-3
            String wikipediaResponceHTML= HttpURLConnectionExample.sendGet(wikiUrl);//CTRL+P we can know what parameter is it expection/reading
            //System.out.println(wikipediaResponceHTML);
            //step-4
            Document document= Jsoup.parse(wikipediaResponceHTML,"https://en.wikipedia.org/wiki/");

            Elements mainElement = document.body().select(".mw-parser-output > *");
            int step = 0;

            //Use of Automate in find table and p tags
            for(Element element : mainElement){
                if(step==0 ) {
                    if (element.tagName().equals("table")) {
                        step = 1;
                    }

                }else if(step == 1){
                    if(element.tagName().equals("p")){
                        step=2;
                        responce= element.text();
                        break;

                    }
                }
            }
            try{
                imageurl = document.body().select(".infobox img").get(0).attr("src");
            }catch (Exception e){
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        WikiResult wikiResult=new WikiResult(this.keyword,responce,imageurl);
        //PUSH RESULT INTO DATABASE

        return wikiResult;
    }

    private String getWikipediaForQuery(String cleanKeyword){

        return "https://en.wikipedia.org/wiki/"+cleanKeyword;
    }
}
