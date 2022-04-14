package com.security_oidc_client.web.controller;

import com.security_oidc_client.domain.DateUtils;
import com.security_oidc_client.domain.TokenHolder;
import com.security_oidc_client.domain.shared.Application;
import com.security_oidc_client.plan.DynamicPlan;
import com.security_oidc_client.plan.NetPlanItem;
import com.security_oidc_client.service.dto.AccessTokenDto;
import com.security_oidc_client.service.dto.CRCCUserInfoDto;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2020-11-04 14:01
 */
public class TokenController {
    public static class TaskAuthLogin implements Runnable {
//        @Autowired
//        private AuthEntityRepository authEntityRepository;
//
//        @Autowired
//        private PostChangeLogEntityRepository postChangeLogEntityRepository;
//
//        @Autowired
//        private OrgLogEntityRepository orgLogEntityRepository;

        TaskAuthLogin() {
        }

        @Override
//        @SneakyThrows
        public void run() {
            try {
                System.out.println("AuthLogin Task begin.");
//                VueRetController.authLogin(authEntityRepository, postChangeLogEntityRepository, orgLogEntityRepository);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("AuthLogin Task end.");
        }
    }

    public static class TaskOrganizationAndUsers implements Runnable {
        int a;
        TaskOrganizationAndUsers() {
        }

        @Override
//        @SneakyThrows
        public void run() {
            try {
                System.out.println("TreeOrganizationAndUsers Task begin.");
//                String nodeProviderId = VueRetController.loginAuthEntity.getNodeProvider();
//                Integer nodeId = VueRetController.loginAuthEntity.getNodeId();
//                Integer nodeType = VueRetController.loginAuthEntity.getNodeType();
//                VueRetController.treeOrganizationAndUsers(nodeProviderId, nodeId, nodeType);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("TreeOrganizationAndUsers Task end.");
        }
    }

    @Test
    public void base64(){
        AccessTokenDto accessTokenDto = TokenHolder.loadAccessToken(Application.getApiRPHolder());
        CRCCUserInfoDto userInfoDto = TokenHolder.getUserApi(accessTokenDto, "/org/"+"hr"+"/user/"+"365474");
        List<CRCCUserInfoDto> userInfoListDto = TokenHolder.getUserListApi(accessTokenDto, "/org/"+"hr"+"/position/"+"366040"+"/users");

        System.out.println(accessTokenDto.getAccessToken());
        System.out.println(userInfoDto.toString());
        System.out.println(userInfoListDto.toString());
        System.out.println(new BCryptPasswordEncoder().encode("user-secret-8888"));
        System.out.println(new BCryptPasswordEncoder().matches("user-secret-8888", "$2a$10$2bv7QExepGFy2Ue7j3X.eOmO7q3MGcVsJOMLWuSXRgrD3XhLuXhQS"));
        System.out.println(new BCryptPasswordEncoder().encode("login-secret-8888"));
        System.out.println(new BCryptPasswordEncoder().matches("login-secret-8888", "$2a$10$rMaxui/kOjUf3lXZw3/jKuCXRLRE7R4ooBXyIdJjo1RR5lRlCMHy2"));

        String auth = "user-client:user-secret-8888";
        byte[] bytes = auth.getBytes();
        //Base64 加密
        String encoded = Base64.getEncoder().encodeToString(bytes);
        System.out.println("Base 64 加密后：" + encoded);
        //Base64 解密
        byte[] decoded = Base64.getDecoder().decode(encoded);
        String decodeStr = new String(decoded);
        System.out.println("Base 64 解密后：" + decodeStr);

        auth = "login-client:login-secret-8888";
        bytes = auth.getBytes();
        //Base64 加密
        encoded = Base64.getEncoder().encodeToString(bytes);
        System.out.println("Base 64 加密后：" + encoded);
        //Base64 解密
        decoded = Base64.getDecoder().decode(encoded);
        decodeStr = new String(decoded);
        System.out.println("Base 64 解密后：" + decodeStr);
    }

    @Test
    public void thread(){
        ExecutorService es = Executors.newCachedThreadPool();
//        ThreadPoolExecutor es = new ThreadPoolExecutor(50, 50, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFactory() {
//            @Override
//            public Thread newThread(Runnable r) {
//                Thread t = new Thread(r);
//                t.setDaemon(true);
//                System.out.println("创建线程"+t);
//                return t;
//            }
//        });

        es.submit(()->{
            try {
                System.out.println("AuthLogin Task begin.");
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("AuthLogin Task end.");
        });
        es.submit(new TaskOrganizationAndUsers());
        es.shutdown();
        System.out.println("ThreadPoolExecutor Start Run Task.");
    }

    @Test
    public void latch() throws InterruptedException {
        System.out.println("Thead Task begin. " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        ExecutorService esMain = Executors.newCachedThreadPool();
        CountDownLatch latchMain = new CountDownLatch(5);

        for(int i=0; i<5; i++) {
            int k = i;
            esMain.submit(() -> {
                System.out.println("Main Task begin. " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

                try {
                    ExecutorService esSub = Executors.newCachedThreadPool();
                    CountDownLatch latchSub = new CountDownLatch(5);

                    List<String> listSub = new ArrayList<>();
                    List<String> list = new ArrayList<>();
                    for (int j = 0; j < 5; j++) {
                        list.add("tread-main-" + k + "-sub-" + j);
                    }

                    for (String x : list) {
                        esSub.submit(() -> {
                            System.out.println(x + " Sub Task begin. " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

                            try {
                                listSub.add(x + " Modify");
                                Thread.sleep(2000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                System.out.println(x + " Sub Task end. " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
                                latchSub.countDown();
                            }
                        });
                    }

                    latchSub.await();
                    System.out.println("Main Task Array Size Is " + listSub.size());
                    esSub.shutdown();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("Main Task end. " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
                    latchMain.countDown();
                }
            });
        }

        latchMain.await();
        System.out.println("Thead Task end. " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
        esMain.shutdown();
    }

    @Test
    public void executor() throws InterruptedException {
        AtomicInteger es_num = new AtomicInteger();
        AtomicInteger es1_num = new AtomicInteger();
        AtomicInteger es2_num = new AtomicInteger();
        ExecutorService es = Executors.newCachedThreadPool();
        ExecutorService es1 = Executors.newCachedThreadPool();
        ExecutorService es2 = Executors.newCachedThreadPool();
        es.submit(() -> {
            try {
                System.out.println("The Main Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
                for (int i=0; i<20; i++) {
                    int finalI = i;
                    es1.submit(() -> {
                        try {
                            for (int j=0; j<50; j++) {
                                int finalJ = j;
                                es2.submit(() -> {
                                    try {
                                        Thread.sleep(3000);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    } finally {
                                        System.out.println("ExecutorService 3 -- " + finalJ +" The Third Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
                                    }
                                });
                            }

                            es2_num.set(((ThreadPoolExecutor) es2).getActiveCount());
//                            System.out.println("es2 count  --->  " + es2_num);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            System.out.println("ExecutorService 2 -- " + finalI +" The Second Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
                        }
                    });
                }

                es1_num.set(((ThreadPoolExecutor) es1).getActiveCount());
//                System.out.println("es1 count  --->  " + es1_num);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("The Main Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
            }
        });

        es_num.set(((ThreadPoolExecutor)es).getActiveCount());
//        System.out.println("es count  --->  " + es_num);


        while(true) {
            if(es_num.get() == 0 && es1_num.get() == 0 && es2_num.get() == 0) {
//                try {
//                    es.shutdown();
//                    if(!es.awaitTermination(5000, TimeUnit.MILLISECONDS)){
//                        es.shutdownNow();
//                    }
//                } catch (InterruptedException e) {
//                    System.out.println("awaitTermination interrupted: " + e);
//                    es.shutdownNow();
//                }

                es2.shutdown();
                es1.shutdown();
                es.shutdown();
                es2.shutdownNow();
                es1.shutdownNow();
                es.shutdownNow();
                System.out.println("----------------------");
                System.out.println("The Main Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
                System.out.println("es count  --->  " + es_num);
                System.out.println("es1 count  --->  " + es1_num);
                System.out.println("es2 count  --->  " + es2_num);
                break;
            } else {
                es_num.set(((ThreadPoolExecutor) es).getActiveCount());
                es1_num.set(((ThreadPoolExecutor) es1).getActiveCount());
                es2_num.set(((ThreadPoolExecutor) es2).getActiveCount());
            }
        }
    }

    @Test
    public void timer() throws Exception {
        VueRetController.timerSyncTreeOrganizationAndUsers(1000L, 10000L);
    }

    @Test
    public void token() {
        String auth = "CRCC12-LDSC:OhOKwUahP9t9rbCjVn38kMt87dHMk03cFXVE48US";
        byte[] bytes = auth.getBytes();
        //Base64 加密
        String encoded = Base64.getEncoder().encodeToString(bytes);
        System.out.println("Base 64 加密后： Basic " + encoded);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Authorization","Basic "+encoded);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("grant_type", "client_credentials");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://regtest.crcc.cn/oauth/token").queryParams(param);
        URI url = builder.build().encode().toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AccessTokenDto> accessTokenDto = restTemplate.exchange(url, HttpMethod.GET, entity, AccessTokenDto.class);
        System.out.println("accessTokenDto = " + accessTokenDto.getBody());

    }

    @Test
    public void test() throws Exception{
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8081/aa";
        //headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("api-version", "1.0");
        //body
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("id", "1");
        //HttpEntity
        HttpEntity<MultiValueMap> requestEntity = new HttpEntity<>(requestBody, requestHeaders);
        //post
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
        System.out.println(responseEntity.getBody());

        ResponseEntity<String> responseEntity1  = restTemplate.exchange("http://172.26.186.206:8080/hive/list/schemas?appid=admin_test",
                HttpMethod.GET, requestEntity, String.class);
        System.out.println(responseEntity1.getBody());
    }

    @Test
    public void treeOrganizationAndUsers() throws Exception {

        ExecutorService esMain = Executors.newCachedThreadPool();
        esMain.submit(()->{
            List<String> listMain = new ArrayList<>();
            CountDownLatch latchMain = new CountDownLatch(5);
            ExecutorService esSub = Executors.newCachedThreadPool();
            List<String> list = new ArrayList<>();
            list.add("tread-1");
            list.add("tread-2");
            list.add("tread-3");
            list.add("tread-4");
            list.add("tread-5");
            for (String x: list) {
                esSub.submit(()->{
                    try {
                        System.out.println(x + " Sub Task begin.");
                        System.out.println(new Date().toString());
                        listMain.add(x+" Modify");
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    System.out.println(x + " Sub Task end.");
                    System.out.println(new Date().toString());
                    latchMain.countDown();
                });
            }

            try {
                latchMain.await();

                System.out.println("Main Task begin.");
                System.out.println(new Date().toString());

                if(!listMain.isEmpty()) {
                    System.out.println("Main Task Array Size Is "+listMain.size());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("Main Task end.");
            System.out.println(new Date().toString());
            esSub.shutdown();
        });
        esMain.shutdown();
    }

    @Test
    public void plan13() throws Exception {
        NetPlanItem[] items = new NetPlanItem[13];
        items[0] = new NetPlanItem(1,0,1,"AAA","[]",4.0);
        items[1] = new NetPlanItem(2,1,2,"AA","[]",7.0);
        items[2] = new NetPlanItem(3,2,3,"A","[]",4.0);
        items[3] = new NetPlanItem(4,2,3,"B","[]",6.0);
        items[4] = new NetPlanItem(5,2,3,"C","[3]",3.0);
        items[5] = new NetPlanItem(6,1,2,"BB","[]",3.0);
        items[6] = new NetPlanItem(7,6,3,"D","[3]",3.0);
        items[7] = new NetPlanItem(8,6,3,"E","[4;5]",2.0);
        items[8] = new NetPlanItem(9,1,2,"CC","[]",5.0);
        items[9] = new NetPlanItem(10,9,3,"F","[7]",5.0);
        items[10] = new NetPlanItem(11,9,3,"G","[7;8]",4.0);
        items[11] = new NetPlanItem(12,1,2,"DD","[]",4.0);
        items[12] = new NetPlanItem(13,12,3,"H","[7;11]",4.0);

        DynamicPlan dynamicPlan = new DynamicPlan(0.0);
        dynamicPlan.initItems(items, 13);
        dynamicPlan.calcPlanItems();
        dynamicPlan.calcStatus(false);
        dynamicPlan.exportItems("D:/item_java.csv");
    }

    @Test
    public void plan6() throws Exception {
        NetPlanItem[] items = new NetPlanItem[6];
        items[0] = new NetPlanItem(1,0,1,"A","[]",5.0);
        items[1] = new NetPlanItem(2,0,1,"B","[1]",6.0);
        items[2] = new NetPlanItem(3,0,1,"C","[1]",3.0);
        items[3] = new NetPlanItem(4,0,1,"D","[2]",4.0);
        items[4] = new NetPlanItem(5,0,1,"E","[2;3]",7.0);
        items[5] = new NetPlanItem(6,0,1,"F","[4;5]",5.0);

        double ES = DateUtils.convertLocalDateTimeToExcelDateTime(LocalDateTime.of(1970,1,1,0,0,0));
        DynamicPlan dynamicPlan = new DynamicPlan(ES);
        dynamicPlan.initItems(items, 6);
        dynamicPlan.calcPlanItems();
        dynamicPlan.calcStatus(false);
        dynamicPlan.exportItems("D:/item_java.csv");
    }

    public static int size = 283;
    public static NetPlanItem[] getItems() {
        NetPlanItem[] items = new NetPlanItem[size];
        items[0] = new NetPlanItem(1,0,1,"升杭大桥-动态计划","[]",128.0);
        items[1] = new NetPlanItem(2,1,2,"地基及基础","[]",47.0);
        items[2] = new NetPlanItem(3,2,3,"钻孔桩","[]",44.0);
        items[3] = new NetPlanItem(4,3,4,"0#台","[]",20.0);
        items[4] = new NetPlanItem(5,4,5,"0-1#桩","[131]",1.0);
        items[5] = new NetPlanItem(6,4,5,"0-2#桩","[5]",1.0);
        items[6] = new NetPlanItem(7,4,5,"0-3#桩","[6]",1.0);
        items[7] = new NetPlanItem(8,4,5,"0-4#桩","[7]",1.0);
        items[8] = new NetPlanItem(9,4,5,"0-5#桩","[8]",1.0);
        items[9] = new NetPlanItem(10,4,5,"0-6#桩","[9]",1.0);
        items[10] = new NetPlanItem(11,4,5,"0-7#桩","[10]",1.0);
        items[11] = new NetPlanItem(12,4,5,"0-8#桩","[11]",1.0);
        items[12] = new NetPlanItem(13,4,5,"0-9#桩","[41]",1.0);
        items[13] = new NetPlanItem(14,4,5,"0-10#桩","[13]",1.0);
        items[14] = new NetPlanItem(15,4,5,"0-11#桩","[14]",1.0);
        items[15] = new NetPlanItem(16,4,5,"0-12#桩","[15]",1.0);
        items[16] = new NetPlanItem(17,4,5,"0-13#桩","[16]",1.0);
        items[17] = new NetPlanItem(18,4,5,"0-14#桩","[17]",1.0);
        items[18] = new NetPlanItem(19,4,5,"0-15#桩","[18]",1.0);
        items[19] = new NetPlanItem(20,4,5,"0-16#桩","[19]",1.0);
        items[20] = new NetPlanItem(21,4,5,"0-17#桩","[54]",1.0);
        items[21] = new NetPlanItem(22,4,5,"0-18#桩","[21]",1.0);
        items[22] = new NetPlanItem(23,4,5,"0-19#桩","[22]",1.0);
        items[23] = new NetPlanItem(24,4,5,"0-20#桩","[23]",1.0);
        items[24] = new NetPlanItem(25,4,5,"0-21#桩","[24]",1.0);
        items[25] = new NetPlanItem(26,4,5,"0-22#桩","[25]",1.0);
        items[26] = new NetPlanItem(27,4,5,"0-23#桩","[26]",1.0);
        items[27] = new NetPlanItem(28,4,5,"0-24#桩","[27]",1.0);
        items[28] = new NetPlanItem(29,3,4,"1#墩","[81]",12.0);
        items[29] = new NetPlanItem(30,29,5,"1-1#桩","[93]",1.0);
        items[30] = new NetPlanItem(31,29,5,"1-2#桩","[30]",1.0);
        items[31] = new NetPlanItem(32,29,5,"1-3#桩","[31]",1.0);
        items[32] = new NetPlanItem(33,29,5,"1-4#桩","[32]",1.0);
        items[33] = new NetPlanItem(34,29,5,"1-5#桩","[33]",1.0);
        items[34] = new NetPlanItem(35,29,5,"1-6#桩","[34]",1.0);
        items[35] = new NetPlanItem(36,29,5,"1-7#桩","[35]",1.0);
        items[36] = new NetPlanItem(37,29,5,"1-8#桩","[36]",1.0);
        items[37] = new NetPlanItem(38,29,5,"1-9#桩","[37]",1.0);
        items[38] = new NetPlanItem(39,29,5,"1-10#桩","[38]",1.0);
        items[39] = new NetPlanItem(40,29,5,"1-11#桩","[39]",1.0);
        items[40] = new NetPlanItem(41,29,5,"1-12#桩","[40]",1.0);
        items[41] = new NetPlanItem(42,3,4,"2#墩","[55]",12.0);
        items[42] = new NetPlanItem(43,42,5,"2-1#桩","[67]",1.0);
        items[43] = new NetPlanItem(44,42,5,"2-2#桩","[43]",1.0);
        items[44] = new NetPlanItem(45,42,5,"2-3#桩","[44]",1.0);
        items[45] = new NetPlanItem(46,42,5,"2-4#桩","[45]",1.0);
        items[46] = new NetPlanItem(47,42,5,"2-5#桩","[46]",1.0);
        items[47] = new NetPlanItem(48,42,5,"2-6#桩","[47]",1.0);
        items[48] = new NetPlanItem(49,42,5,"2-7#桩","[48]",1.0);
        items[49] = new NetPlanItem(50,42,5,"2-8#桩","[49]",1.0);
        items[50] = new NetPlanItem(51,42,5,"2-9#桩","[50]",1.0);
        items[51] = new NetPlanItem(52,42,5,"2-10#桩","[51]",1.0);
        items[52] = new NetPlanItem(53,42,5,"2-11#桩","[52]",1.0);
        items[53] = new NetPlanItem(54,42,5,"2-12#桩","[53]",1.0);
        items[54] = new NetPlanItem(55,3,4,"3#墩","[68]",12.0);
        items[55] = new NetPlanItem(56,55,5,"3-1#桩","[80]",1.0);
        items[56] = new NetPlanItem(57,55,5,"3-2#桩","[56]",1.0);
        items[57] = new NetPlanItem(58,55,5,"3-3#桩","[57]",1.0);
        items[58] = new NetPlanItem(59,55,5,"3-4#桩","[58]",1.0);
        items[59] = new NetPlanItem(60,55,5,"3-5#桩","[59]",1.0);
        items[60] = new NetPlanItem(61,55,5,"3-6#桩","[60]",1.0);
        items[61] = new NetPlanItem(62,55,5,"3-7#桩","[61]",1.0);
        items[62] = new NetPlanItem(63,55,5,"3-8#桩","[62]",1.0);
        items[63] = new NetPlanItem(64,55,5,"3-9#桩","[63]",1.0);
        items[64] = new NetPlanItem(65,55,5,"3-10#桩","[64]",1.0);
        items[65] = new NetPlanItem(66,55,5,"3-11#桩","[65]",1.0);
        items[66] = new NetPlanItem(67,55,5,"3-12#桩","[66]",1.0);
        items[67] = new NetPlanItem(68,3,4,"4#墩","[]",12.0);
        items[68] = new NetPlanItem(69,68,5,"4-1#桩","[]",1.0);
        items[69] = new NetPlanItem(70,68,5,"4-2#桩","[69]",1.0);
        items[70] = new NetPlanItem(71,68,5,"4-3#桩","[70]",1.0);
        items[71] = new NetPlanItem(72,68,5,"4-4#桩","[71]",1.0);
        items[72] = new NetPlanItem(73,68,5,"4-5#桩","[72]",1.0);
        items[73] = new NetPlanItem(74,68,5,"4-6#桩","[73]",1.0);
        items[74] = new NetPlanItem(75,68,5,"4-7#桩","[74]",1.0);
        items[75] = new NetPlanItem(76,68,5,"4-8#桩","[75]",1.0);
        items[76] = new NetPlanItem(77,68,5,"4-9#桩","[76]",1.0);
        items[77] = new NetPlanItem(78,68,5,"4-10#桩","[77]",1.0);
        items[78] = new NetPlanItem(79,68,5,"4-11#桩","[78]",1.0);
        items[79] = new NetPlanItem(80,68,5,"4-12#桩","[79]",1.0);
        items[80] = new NetPlanItem(81,3,4,"5#墩","[94]",12.0);
        items[81] = new NetPlanItem(82,81,5,"5-1#桩","[106]",1.0);
        items[82] = new NetPlanItem(83,81,5,"5-2#桩","[82]",1.0);
        items[83] = new NetPlanItem(84,81,5,"5-3#桩","[83]",1.0);
        items[84] = new NetPlanItem(85,81,5,"5-4#桩","[84]",1.0);
        items[85] = new NetPlanItem(86,81,5,"5-5#桩","[85]",1.0);
        items[86] = new NetPlanItem(87,81,5,"5-6#桩","[86]",1.0);
        items[87] = new NetPlanItem(88,81,5,"5-7#桩","[87]",1.0);
        items[88] = new NetPlanItem(89,81,5,"5-8#桩","[88]",1.0);
        items[89] = new NetPlanItem(90,81,5,"5-9#桩","[89]",1.0);
        items[90] = new NetPlanItem(91,81,5,"5-10#桩","[90]",1.0);
        items[91] = new NetPlanItem(92,81,5,"5-11#桩","[91]",1.0);
        items[92] = new NetPlanItem(93,81,5,"5-12#桩","[92]",1.0);
        items[93] = new NetPlanItem(94,3,4,"6#墩","[]",12.0);
        items[94] = new NetPlanItem(95,94,5,"6-1#桩","[]",1.0);
        items[95] = new NetPlanItem(96,94,5,"6-2#桩","[95]",1.0);
        items[96] = new NetPlanItem(97,94,5,"6-3#桩","[96]",1.0);
        items[97] = new NetPlanItem(98,94,5,"6-4#桩","[97]",1.0);
        items[98] = new NetPlanItem(99,94,5,"6-5#桩","[98]",1.0);
        items[99] = new NetPlanItem(100,94,5,"6-6#桩","[99]",1.0);
        items[100] = new NetPlanItem(101,94,5,"6-7#桩","[100]",1.0);
        items[101] = new NetPlanItem(102,94,5,"6-8#桩","[101]",1.0);
        items[102] = new NetPlanItem(103,94,5,"6-9#桩","[102]",1.0);
        items[103] = new NetPlanItem(104,94,5,"6-10#桩","[103]",1.0);
        items[104] = new NetPlanItem(105,94,5,"6-11#桩","[104]",1.0);
        items[105] = new NetPlanItem(106,94,5,"6-12#桩","[105]",1.0);
        items[106] = new NetPlanItem(107,3,4,"7#台","[]",24.0);
        items[107] = new NetPlanItem(108,107,5,"7-1#桩","[]",1.0);
        items[108] = new NetPlanItem(109,107,5,"7-2#桩","[108]",1.0);
        items[109] = new NetPlanItem(110,107,5,"7-3#桩","[109]",1.0);
        items[110] = new NetPlanItem(111,107,5,"7-4#桩","[110]",1.0);
        items[111] = new NetPlanItem(112,107,5,"7-5#桩","[111]",1.0);
        items[112] = new NetPlanItem(113,107,5,"7-6#桩","[112]",1.0);
        items[113] = new NetPlanItem(114,107,5,"7-7#桩","[113]",1.0);
        items[114] = new NetPlanItem(115,107,5,"7-8#桩","[114]",1.0);
        items[115] = new NetPlanItem(116,107,5,"7-9#桩","[115]",1.0);
        items[116] = new NetPlanItem(117,107,5,"7-10#桩","[116]",1.0);
        items[117] = new NetPlanItem(118,107,5,"7-11#桩","[117]",1.0);
        items[118] = new NetPlanItem(119,107,5,"7-12#桩","[118]",1.0);
        items[119] = new NetPlanItem(120,107,5,"7-13#桩","[119]",1.0);
        items[120] = new NetPlanItem(121,107,5,"7-14#桩","[120]",1.0);
        items[121] = new NetPlanItem(122,107,5,"7-15#桩","[121]",1.0);
        items[122] = new NetPlanItem(123,107,5,"7-16#桩","[122]",1.0);
        items[123] = new NetPlanItem(124,107,5,"7-17#桩","[123]",1.0);
        items[124] = new NetPlanItem(125,107,5,"7-18#桩","[124]",1.0);
        items[125] = new NetPlanItem(126,107,5,"7-19#桩","[125]",1.0);
        items[126] = new NetPlanItem(127,107,5,"7-20#桩","[126]",1.0);
        items[127] = new NetPlanItem(128,107,5,"7-21#桩","[127]",1.0);
        items[128] = new NetPlanItem(129,107,5,"7-22#桩","[128]",1.0);
        items[129] = new NetPlanItem(130,107,5,"7-23#桩","[129]",1.0);
        items[130] = new NetPlanItem(131,107,5,"7-24#桩","[130]",1.0);
        items[131] = new NetPlanItem(132,2,3,"承台","[]",35.0);
        items[132] = new NetPlanItem(133,132,4,"0#台","[4]",3.0);
        items[133] = new NetPlanItem(134,133,5,"0#台承台","[12;20;28]",3.0);
        items[134] = new NetPlanItem(135,132,4,"1#墩","[29]",3.0);
        items[135] = new NetPlanItem(136,135,5,"1#墩承台","[41]",3.0);
        items[136] = new NetPlanItem(137,132,4,"2#墩","[42]",3.0);
        items[137] = new NetPlanItem(138,137,5,"2#墩承台","[54]",3.0);
        items[138] = new NetPlanItem(139,132,4,"3#墩","[55]",3.0);
        items[139] = new NetPlanItem(140,139,5,"3#墩承台","[67]",3.0);
        items[140] = new NetPlanItem(141,132,4,"4#墩","[68]",3.0);
        items[141] = new NetPlanItem(142,141,5,"4#墩承台","[80]",3.0);
        items[142] = new NetPlanItem(143,132,4,"5#墩","[81]",3.0);
        items[143] = new NetPlanItem(144,143,5,"5#墩承台","[93]",3.0);
        items[144] = new NetPlanItem(145,132,4,"6#墩","[94]",3.0);
        items[145] = new NetPlanItem(146,145,5,"6#墩承台","[106]",3.0);
        items[146] = new NetPlanItem(147,132,4,"7#台","[107]",3.0);
        items[147] = new NetPlanItem(148,147,5,"7#台承台","[131]",3.0);
        items[148] = new NetPlanItem(149,1,2,"墩台","[]",47.0);
        items[149] = new NetPlanItem(150,149,3,"墩身","[]",30.0);
        items[150] = new NetPlanItem(151,150,4,"1#墩","[135]",6.0);
        items[151] = new NetPlanItem(152,151,5,"1#墩墩身","[136]",4.0);
        items[152] = new NetPlanItem(153,151,5,"1#墩顶帽","[152]",2.0);
        items[153] = new NetPlanItem(154,150,4,"2#墩","[137]",6.0);
        items[154] = new NetPlanItem(155,154,5,"2#墩墩身","[138]",4.0);
        items[155] = new NetPlanItem(156,154,5,"2#墩顶帽","[155]",2.0);
        items[156] = new NetPlanItem(157,150,4,"3#墩","[139]",6.0);
        items[157] = new NetPlanItem(158,157,5,"3#墩墩身","[140]",4.0);
        items[158] = new NetPlanItem(159,157,5,"3#墩顶帽","[158]",2.0);
        items[159] = new NetPlanItem(160,150,4,"4#墩","[141]",6.0);
        items[160] = new NetPlanItem(161,160,5,"4#墩墩身","[142]",4.0);
        items[161] = new NetPlanItem(162,160,5,"4#墩顶帽","[161]",2.0);
        items[162] = new NetPlanItem(163,150,4,"5#墩","[143]",6.0);
        items[163] = new NetPlanItem(164,163,5,"5#墩墩身","[144]",4.0);
        items[164] = new NetPlanItem(165,163,5,"5#墩顶帽","[164]",2.0);
        items[165] = new NetPlanItem(166,150,4,"6#墩","[145]",6.0);
        items[166] = new NetPlanItem(167,166,5,"6#墩墩身","[146]",4.0);
        items[167] = new NetPlanItem(168,166,5,"6#墩顶帽","[167]",2.0);
        items[168] = new NetPlanItem(169,149,3,"台身","[]",27.0);
        items[169] = new NetPlanItem(170,169,4,"0#台","[133]",7.0);
        items[170] = new NetPlanItem(171,170,5,"0#台台身","[134]",4.0);
        items[171] = new NetPlanItem(172,170,5,"0#台顶帽","[171]",2.0);
        items[172] = new NetPlanItem(173,170,5,"0#台台顶","[172]",1.0);
        items[173] = new NetPlanItem(174,169,4,"7#台","[147]",7.0);
        items[174] = new NetPlanItem(175,174,5,"7#台台身","[148]",4.0);
        items[175] = new NetPlanItem(176,174,5,"7#台顶帽","[175]",2.0);
        items[176] = new NetPlanItem(177,174,5,"7#台台顶","[176]",1.0);
        items[177] = new NetPlanItem(178,149,3,"支承垫石","[]",8.0);
        items[178] = new NetPlanItem(179,178,4,"0#台","[170]",1.0);
        items[179] = new NetPlanItem(180,179,5,"0#台支承垫石","[173]",1.0);
        items[180] = new NetPlanItem(181,178,4,"1#墩","[179;151]",1.0);
        items[181] = new NetPlanItem(182,181,5,"1#墩支承垫石","[180;153]",1.0);
        items[182] = new NetPlanItem(183,178,4,"2#墩","[181;154]",1.0);
        items[183] = new NetPlanItem(184,183,5,"2#墩支承垫石","[182;156]",1.0);
        items[184] = new NetPlanItem(185,178,4,"3#墩","[183;157]",1.0);
        items[185] = new NetPlanItem(186,185,5,"3#墩支承垫石","[184;159]",1.0);
        items[186] = new NetPlanItem(187,178,4,"4#墩","[185;160]",1.0);
        items[187] = new NetPlanItem(188,187,5,"4#墩支承垫石","[186;162]",1.0);
        items[188] = new NetPlanItem(189,178,4,"5#墩","[187;163]",1.0);
        items[189] = new NetPlanItem(190,189,5,"5#墩支承垫石","[188;165]",1.0);
        items[190] = new NetPlanItem(191,178,4,"6#墩","[189;166]",1.0);
        items[191] = new NetPlanItem(192,191,5,"6#墩支承垫石","[190;168]",1.0);
        items[192] = new NetPlanItem(193,178,4,"7#台","[191;174]",1.0);
        items[193] = new NetPlanItem(194,193,5,"7#台支承垫石","[192;177]",1.0);
        items[194] = new NetPlanItem(195,149,3,"锥体","[]",24.0);
        items[195] = new NetPlanItem(196,195,4,"0#台","[170]",4.0);
        items[196] = new NetPlanItem(197,196,5,"0#台锥体","[173]",4.0);
        items[197] = new NetPlanItem(198,195,4,"7#台","[174]",4.0);
        items[198] = new NetPlanItem(199,198,5,"7#台锥体","[177]",4.0);
        items[199] = new NetPlanItem(200,149,3,"排水设施","[]",24.0);
        items[200] = new NetPlanItem(201,200,4,"0#台","[196]",4.0);
        items[201] = new NetPlanItem(202,201,5,"0#台排水设施","[197]",4.0);
        items[202] = new NetPlanItem(203,200,4,"7#台","[198]",4.0);
        items[203] = new NetPlanItem(204,203,5,"7#台排水设施","[199]",4.0);
        items[204] = new NetPlanItem(205,1,2,"结合梁","[]",31.0);
        items[205] = new NetPlanItem(206,205,3,"钢梁","[]",24.0);
        items[206] = new NetPlanItem(207,206,4,"第1跨","[193;179;181]",5.0);
        items[207] = new NetPlanItem(208,207,5,"第1跨钢梁","[180;182;194]",5.0);
        items[208] = new NetPlanItem(209,206,4,"第2跨","[207;183]",5.0);
        items[209] = new NetPlanItem(210,209,5,"第2跨钢梁","[208;184]",5.0);
        items[210] = new NetPlanItem(211,206,4,"第3跨","[209;185]",3.0);
        items[211] = new NetPlanItem(212,211,5,"第3跨钢梁","[210;186]",3.0);
        items[212] = new NetPlanItem(213,206,4,"第4跨","[211;187]",3.0);
        items[213] = new NetPlanItem(214,213,5,"第4跨钢梁","[212;188]",3.0);
        items[214] = new NetPlanItem(215,206,4,"第5跨","[213;189]",2.0);
        items[215] = new NetPlanItem(216,215,5,"第5跨钢梁","[214;190]",2.0);
        items[216] = new NetPlanItem(217,206,4,"第6跨","[215;191]",2.0);
        items[217] = new NetPlanItem(218,217,5,"第6跨钢梁","[216;192]",2.0);
        items[218] = new NetPlanItem(219,206,4,"第7跨","[217]",4.0);
        items[219] = new NetPlanItem(220,219,5,"第7跨钢梁","[218]",4.0);
        items[220] = new NetPlanItem(221,205,3,"桥面板","[]",7.0);
        items[221] = new NetPlanItem(222,221,4,"第1跨","[219]",1.0);
        items[222] = new NetPlanItem(223,222,5,"第1跨桥面板","[220]",1.0);
        items[223] = new NetPlanItem(224,221,4,"第2跨","[222]",1.0);
        items[224] = new NetPlanItem(225,224,5,"第2跨桥面板","[223]",1.0);
        items[225] = new NetPlanItem(226,221,4,"第3跨","[224]",1.0);
        items[226] = new NetPlanItem(227,226,5,"第3跨桥面板","[225]",1.0);
        items[227] = new NetPlanItem(228,221,4,"第4跨","[226]",1.0);
        items[228] = new NetPlanItem(229,228,5,"第4跨桥面板","[227]",1.0);
        items[229] = new NetPlanItem(230,221,4,"第5跨","[228]",1.0);
        items[230] = new NetPlanItem(231,230,5,"第5跨桥面板","[229]",1.0);
        items[231] = new NetPlanItem(232,221,4,"第6跨","[230]",1.0);
        items[232] = new NetPlanItem(233,232,5,"第6跨桥面板","[231]",1.0);
        items[233] = new NetPlanItem(234,221,4,"第7跨","[232]",1.0);
        items[234] = new NetPlanItem(235,234,5,"第7跨桥面板","[233]",1.0);
        items[235] = new NetPlanItem(236,1,2,"桥梁附属设施","[]",74.0);
        items[236] = new NetPlanItem(237,236,3,"防护墙","[]",21.0);
        items[237] = new NetPlanItem(238,237,4,"第1跨","[234]",3.0);
        items[238] = new NetPlanItem(239,238,5,"第1跨防护墙","[235]",3.0);
        items[239] = new NetPlanItem(240,237,4,"第2跨","[238]",3.0);
        items[240] = new NetPlanItem(241,240,5,"第2跨防护墙","[239]",3.0);
        items[241] = new NetPlanItem(242,237,4,"第3跨","[240]",3.0);
        items[242] = new NetPlanItem(243,242,5,"第3跨防护墙","[241]",3.0);
        items[243] = new NetPlanItem(244,237,4,"第4跨","[242]",3.0);
        items[244] = new NetPlanItem(245,244,5,"第4跨防护墙","[243]",3.0);
        items[245] = new NetPlanItem(246,237,4,"第5跨","[244]",3.0);
        items[246] = new NetPlanItem(247,246,5,"第5跨防护墙","[245]",3.0);
        items[247] = new NetPlanItem(248,237,4,"第6跨","[246]",3.0);
        items[248] = new NetPlanItem(249,248,5,"第6跨防护墙","[247]",3.0);
        items[249] = new NetPlanItem(250,237,4,"第7跨","[248]",3.0);
        items[250] = new NetPlanItem(251,250,5,"第7跨防护墙","[249]",3.0);
        items[251] = new NetPlanItem(252,236,3,"栏杆","[]",14.0);
        items[252] = new NetPlanItem(253,252,4,"第1跨","[250]",2.0);
        items[253] = new NetPlanItem(254,253,5,"第1跨栏杆","[251]",2.0);
        items[254] = new NetPlanItem(255,252,4,"第2跨","[253]",2.0);
        items[255] = new NetPlanItem(256,255,5,"第2跨栏杆","[254]",2.0);
        items[256] = new NetPlanItem(257,252,4,"第3跨","[255]",2.0);
        items[257] = new NetPlanItem(258,257,5,"第3跨栏杆","[256]",2.0);
        items[258] = new NetPlanItem(259,252,4,"第4跨","[257]",2.0);
        items[259] = new NetPlanItem(260,259,5,"第4跨栏杆","[258]",2.0);
        items[260] = new NetPlanItem(261,252,4,"第5跨","[259]",2.0);
        items[261] = new NetPlanItem(262,261,5,"第5跨栏杆","[260]",2.0);
        items[262] = new NetPlanItem(263,252,4,"第6跨","[261]",2.0);
        items[263] = new NetPlanItem(264,263,5,"第6跨栏杆","[262]",2.0);
        items[264] = new NetPlanItem(265,252,4,"第7跨","[263]",2.0);
        items[265] = new NetPlanItem(266,265,5,"第7跨栏杆","[264]",2.0);
        items[266] = new NetPlanItem(267,236,3,"吊围栏","[]",32.0);
        items[267] = new NetPlanItem(268,267,4,"0#台","[170]",4.0);
        items[268] = new NetPlanItem(269,268,5,"0#台吊围栏","[173]",4.0);
        items[269] = new NetPlanItem(270,267,4,"1#墩","[151;268]",4.0);
        items[270] = new NetPlanItem(271,270,5,"1#墩吊围栏","[153;269]",4.0);
        items[271] = new NetPlanItem(272,267,4,"2#墩","[154;270]",4.0);
        items[272] = new NetPlanItem(273,272,5,"2#墩吊围栏","[156;271]",4.0);
        items[273] = new NetPlanItem(274,267,4,"3#墩","[157;272]",4.0);
        items[274] = new NetPlanItem(275,274,5,"3#墩吊围栏","[159;273]",4.0);
        items[275] = new NetPlanItem(276,267,4,"4#墩","[160;274]",4.0);
        items[276] = new NetPlanItem(277,276,5,"4#墩吊围栏","[162;275]",4.0);
        items[277] = new NetPlanItem(278,267,4,"5#墩","[163;276]",4.0);
        items[278] = new NetPlanItem(279,278,5,"5#墩吊围栏","[165;277]",4.0);
        items[279] = new NetPlanItem(280,267,4,"6#墩","[166;278]",4.0);
        items[280] = new NetPlanItem(281,280,5,"6#墩吊围栏","[168;279]",4.0);
        items[281] = new NetPlanItem(282,267,4,"7#台","[174;280]",4.0);
        items[282] = new NetPlanItem(283,282,5,"7#台吊围栏","[177;281]",4.0);

        return items;
    }
    public static void main(String[] args) throws Exception {
        NetPlanItem[] items = getItems();
        double ES = DateUtils.convertLocalDateTimeToExcelDateTime(LocalDateTime.of(2021,6,2,14,0,0));
        DynamicPlan dynamicPlan = new DynamicPlan(ES);
        dynamicPlan.initItems(items, size);
        dynamicPlan.calcPlanItems();
        dynamicPlan.calcEstimateItems(80, 2, 44371.0);
        dynamicPlan.calcEstimateItems(79, 3, 10.0);
        dynamicPlan.calcActualItems(23, 44407.0);
        dynamicPlan.calcActualItems(180, 44428.5833333333);
        dynamicPlan.calcStatus(false);
        dynamicPlan.exportItems("D:/item_java.csv");
    }
}
