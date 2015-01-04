package org.pojo;

/**
 * @author wally
 * @version 1.0
 * @email hongwei200612@gmail.com
 * @created 2014/6/23 20:27
 * @description 插入数据库的中间对象，为传值方便
 */
public class Data {
    private String        id;  //唯一主键防止重新插入
    private int           rtype; //日志类型
    private Ad            ad; //广告内容
    private AdFeedback    feedback; //客户端回执
    private App           app;
    private ClientMessage clientMessage;
    private String        result;  //不发广告原因
    private long          time;

    public Data() {
//        this.id = ObjectId.get().toString();
    }

    public Data(App app, ClientMessage clientMessage, int rtype, String result) {
        this.app = app;
        this.clientMessage = clientMessage;
        this.rtype = rtype;
        this.result = result;
//        this.id = ObjectId.get().toString();
    }

    public Data(Ad ad, AdFeedback feedback, App app, ClientMessage clientMessage, int rtype) {
        this.ad = ad;
        this.feedback = feedback;
        this.app = app;
        this.clientMessage = clientMessage;
        this.rtype = rtype;
//        this.id = ObjectId.get().toString();
    }

    public Data(Ad ad, AdFeedback feedback, App app, ClientMessage clientMessage, int rtype, String result) {
        this.ad = ad;
        this.feedback = feedback;
        this.app = app;
        this.clientMessage = clientMessage;
        this.rtype = rtype;
        this.result = result;
//        this.id = ObjectId.get().toString();
    }

//    public static DBObject parse(Data data) throws JsonProcessingException {
//        objectMapper = new ObjectMapper();
//        objectMapper.setSerializationInclusion(Include.NON_NULL);
//        BasicDBObject bdo = new BasicDBObject();
//        bdo.put("rtype", data.getRtype());
//        if (Constants.RTYPE_ALL == data.getRtype() || Constants.RTYPE_REQUEST == data.getRtype() || Constants.RTYPE_INFO == data.getRtype() || Constants.RTYPE_START == data.getRtype()) {
//            bdo.put("clientMessage", JSON.parse(objectMapper.writeValueAsString(data.getClientMessage())));
//            bdo.put("result", data.getResult());
//        } else {
//            bdo.put("ad", JSON.parse(objectMapper.writeValueAsString(data.getAd())));
//            bdo.put("app", JSON.parse(objectMapper.writeValueAsString(data.getApp())));
//            bdo.put("feedback", JSON.parse(objectMapper.writeValueAsString(data.getFeedback())));
//        }
//        bdo.put("time", new Date().getTime());
//        bdo.put("_id", data.getId());
//        return bdo;
//    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getRtype() {
        return rtype;
    }

    public void setRtype(int rtype) {
        this.rtype = rtype;
    }

    public Ad getAd() {
        if (this.ad == null) ad = new Ad();
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public AdFeedback getFeedback() {
        if (feedback == null) feedback = new AdFeedback();
        return feedback;
    }

    public void setFeedback(AdFeedback feedback) {
        this.feedback = feedback;
    }

    public App getApp() {
        if (this.app == null) app = new App();
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public ClientMessage getClientMessage() {
        if (clientMessage == null) clientMessage = new ClientMessage();
        return clientMessage;
    }

    public void setClientMessage(ClientMessage clientMessage) {
        this.clientMessage = clientMessage;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
