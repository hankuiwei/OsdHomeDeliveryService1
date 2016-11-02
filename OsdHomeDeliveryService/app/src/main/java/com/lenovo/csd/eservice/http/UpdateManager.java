package com.lenovo.csd.eservice.http;

import android.os.Looper;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by 彤 on 2016/8/1.
 */
public class UpdateManager {
    public static void getLatestAppInfo(final AppInfoLinstenner linstenner) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UpdateInfo info = null;
                try {
                    Looper.prepare();
                    URL url = new URL(NetInterface.URL_APP_UPDATE);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.setConnectTimeout(5 * 1000);
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document document = builder.parse(inputStream);
                    info = new UpdateInfo();
                    //获取根节点
                    Element element = document.getDocumentElement();
                    NodeList nodes = element.getChildNodes();
                    for (int index = 0; index < nodes.getLength(); index++) {
                        Node node = nodes.item(index);
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            Element element_child = (Element) node;
                            if (element_child.getNodeName().equals("currentversion")) {
                                info.setVersion(element_child.getFirstChild().getNodeValue());
                            } else if (element_child.getNodeName().equals("minversion")) {
                                info.setMinVersion(element_child.getFirstChild().getNodeValue());
                            } else if (element_child.getNodeName().equals("downloadurl")) {
                                info.setDlurl(element_child.getFirstChild().getNodeValue());
                            } else if (element_child.getNodeName().equals("releasenote")) {
                                NodeList list = element_child.getChildNodes();
                                List<String> notes = new ArrayList<String>();
                                for (int i = 0; i < list.getLength(); i++) {
                                    Node node1 = list.item(i);
                                    if (node1.getNodeType() == Node.ELEMENT_NODE) {
                                        Element item = (Element) node1;
                                        if (item.getNodeName().equals("item")) {
                                            notes.add(item.getFirstChild().getNodeValue());
                                        }
                                    }
                                }
                                info.setNotes(notes);
                            }
                        }
                    }
                    inputStream.close();
                    connection.disconnect();
                    linstenner.onResult(info);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 获取最新app信息的回调
     */
    public interface AppInfoLinstenner {
        void onResult(UpdateInfo info);
    }

    public static class UpdateInfo {
        private String version;
        private String dlurl;
        private String minVersion;
        private List<String> notes;


        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getDlurl() {
            return dlurl;
        }

        public void setDlurl(String dlurl) {
            this.dlurl = dlurl;
        }

        public String getMinVersion() {
            return minVersion;
        }

        public void setMinVersion(String minVersion) {
            this.minVersion = minVersion;
        }

        public List<String> getNotes() {
            return notes;
        }

        public void setNotes(List<String> notes) {
            this.notes = notes;
        }
    }
}
