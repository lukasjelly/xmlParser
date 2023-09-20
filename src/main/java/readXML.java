import nz.ac.massey.cs.sdc.parsers.Rss;
import nz.ac.massey.cs.sdc.parsers.RssItem;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import java.io.File;
import java.util.List;

public class readXML {
    public static void main(String[] args) {
        try {
            JAXBContext jc = JAXBContext.newInstance("nz.ac.massey.cs.sdc.parsers");
            Unmarshaller parser = jc.createUnmarshaller();
            File file = new File("src/main/xml/download.xml");
            Rss rss = (Rss) parser.unmarshal(file);
            List<RssItem> rssItems = rss.getChannel().getItem();

            for (RssItem item : rssItems) {
                List<Object> itemContent = item.getTitleOrDescriptionOrLink();
                String title = null;
                String description = null;
                String link = null;

                for (Object content : itemContent) {
                    if (content instanceof JAXBElement) {
                        JAXBElement<?> element = (JAXBElement<?>) content;
                        if ("title".equals(element.getName().getLocalPart())) {
                            title = (String) element.getValue();
                        } else if ("description".equals(element.getName().getLocalPart())) {
                            description = (String) element.getValue();
                        } else if ("link".equals(element.getName().getLocalPart())) {
                            link = (String) element.getValue();
                        }
                    }
                }
                System.out.println("Title: " + title.replaceAll("\\s+", " "));
                System.out.println("Description: " + description.replaceAll("\\s+", " "));
                System.out.println("Link: " + link.replaceAll("\\s+", " "));
                System.out.println();


            }
        } catch (JAXBException ex) {
            System.out.println(ex);
        }
    }
}
