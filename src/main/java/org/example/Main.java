package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        try {
            Chat chat = parser.parseChat(new File("C:\\Users\\Mi\\TG_ANALYSE\\ConvoMetrics\\chat.json"), false);
            Analyzer analyzer = new Analyzer(chat);
            ChartGenerator chartGenerator = new ChartGenerator(analyzer);
            chartGenerator.generateCharts("./output/ready-html/images/");
            generateHTML(analyzer, chat);

            System.out.println(analyzer.getMessagesCount());
            analyzer.getCountOfMessagesPerUser().entrySet().forEach(System.out::println);
//            analyzer.getCountOfMessagesPerDay().entrySet().forEach(System.out::println);
//            analyzer.getCountOfMessagesPerDayPerUser().entrySet().forEach(System.out::println);
//            analyzer.getCountOfMessagesPerDayOfWeek().entrySet().forEach(System.out::println);
//            analyzer.getCountOfMessagesPerDayOfWeekPerUser().entrySet().forEach(System.out::println);
//            analyzer.getCountOfMessagesPerHour().entrySet().forEach(System.out::println);
//            analyzer.getCountOfMessagesPerHourPerUser().entrySet().forEach(System.out::println);
//            analyzer.getMostPopularMessages()
//                    .entrySet()
//                    .stream()
//                    .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // сортировка по значению по убыванию
//                    .forEach(entry -> System.out.println("Message: " + entry.getKey() + ", Count: " + entry.getValue()));
//            analyzer.getMostPopularMessagesPerUser()
//                    .forEach((user, messagesMap) -> {
//                        System.out.println("User: " + user);
//                        messagesMap.entrySet()
//                                .stream()
//                                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // сортировка по убыванию
//                                .forEach(entry -> System.out.println("  Message: " + entry.getKey() + ", Count: " + entry.getValue()));
//                    });
//            analyzer.getMostPopularWords()
//                    .entrySet()
//                    .stream()
//                    .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // сортировка по значению по убыванию
//                    .forEach(entry -> System.out.println("Message: " + entry.getKey() + ", Count: " + entry.getValue()));
//            analyzer.getMostPopularWordsPerUser()
//                    .forEach((user, messagesMap) -> {
//                        System.out.println("User: " + user);
//                        messagesMap.entrySet()
//                                .stream()
//                                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // сортировка по убыванию
//                                .forEach(entry -> System.out.println("  Message: " + entry.getKey() + ", Count: " + entry.getValue()));
//                    });


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void generateHTML(Analyzer analyzer, Chat chat) {
        String[] names = analyzer.getCountOfMessagesPerUser().keySet().toArray(new String[0]);
        List<Map.Entry<String, Integer>> topMessages0 = analyzer.getMostPopularMessagesPerUser().get(names[0]).entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(10)
                .toList();
        List<Map.Entry<String, Integer>> topMessages1 = analyzer.getMostPopularMessagesPerUser().get(names[1]).entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(10)
                .toList();
        String result = String.format("""
                <!DOCTYPE html>
                <html lang="en">
                <head>
                	<meta charset="UTF-8">
                	<meta http-equiv="X-UA-Compatible" content="IE=edge">
                	<meta name="viewport" content="width=device-width, initial-scale=1.0">
                	<title>Document</title>
                    
                	<link rel="stylesheet" href="css/main.css">
                	<script src="js/app.js" defer></script>
                    
                </head>
                <body>
                    
                	<div class="container">
                		<section class="gallery">
                    
                			<div class="frame">
                				<div class="frame__content">
                					<h2>%s...</h2>
                				</div>
                			</div>
                    
                			<div class="frame"></div>
                    
                			<div class="frame frame_bg">
                				<div class="frame__content">
                					<div class="frame-media frame-media_right" style="background-image: url(images/couple.png)"></div>
                				</div>
                			</div>
                    
                			<div class="frame">
                				<div class="frame__content text-left">
                					<h3>Как это было?</h3>
                					<p>За %d дней переписки вы написали %d сообщений</p>
                					<p>В среднем это %.2f сообщения и %.2f слова в день</p>
                				</div>
                			</div>
                    
                			<div class="frame">
                				<div class="frame__content">
                					<div class="frame-media" style="background-image: url(images/messages_per_day.png)"></div>
                				</div>
                			</div>
                    
                			<div class="frame">
                				<div class="frame__content text-right">
                					<h3>При этом</h3>
                					<p>%s написал(а) %d сообщений и %d слов</p>
                					<p>Это %.2f слова в каждом сообщении</p>
                				</div>
                			</div>
                    
                			<div class="frame">
                				<div class="frame__content text-left">
                					<p>А %s написал(а) %d сообщений и %d слов</p>
                					<p>И это %.2f слова в каждом сообщении</p>
                				</div>
                			</div>
                    
                			<div class="frame"></div>
                			<div class="frame"></div>
                    
                			<div class="frame">
                				<div class="frame__content">
                					<div class="frame-media frame-media_right" style="background-image: url(images/messages_per_user.png)"></div>
                				</div>
                			</div>
                    
                			<div class="frame">
                				<div class="frame__content text-right">
                					<h3>Ой</h3>
                					<p>А кто это не спит ночью?</p>
                				</div>
                			</div>
                    
                			<div class="frame">
                				<div class="frame__content">
                					<div class="frame-media frame-media_left" style="background-image: url(images/messages_per_hour.png)"></div>
                				</div>
                			</div>
                    
                			<div class="frame">
                				<div class="frame__content text-left">
                					<h3>Спишемся на выходных</h3>
                					<p>Got to go</p>
                				</div>
                			</div>
                    
                			<div class="frame">
                				<div class="frame__content">
                					<div class="frame-media frame-media_right" style="background-image: url(images/messages_per_day_of_week.png)"></div>
                				</div>
                			</div>
                    
                			<div class="frame">
                				<div class="frame__content text-right">
                					<h3>%s</h3>
                					<p>Твои самые частые сообщения</p>
                				</div>
                			</div>
                    
                			<div class="frame">
                				<div class="frame__content text-left">
                					<h3>%s</h3>
                					<p>Твои самые частые сообщения</p>
                				</div>
                			</div>
                    
                			<div class="frame"></div>
                    
                <!--			1-->
                			<div class="frame">
                				<div class="frame__content text-right">
                					<h5>%d раз</h5>
                					<p>%s</p>
                				</div>
                			</div>
                    
                			<div class="frame">
                				<div class="frame__content text-left">
                					<h5>%d раз</h5>
                					<p>%s</p>
                				</div>
                			</div>
                    
                			<div class="frame"></div>
                    
                <!--			2-->
                			<div class="frame">
                				<div class="frame__content text-right">
                					<h5>%d раз</h5>
                					<p>%s</p>
                				</div>
                			</div>
                    
                			<div class="frame">
                				<div class="frame__content text-left">
                					<h5>%d раз</h5>
                					<p>%s</p>
                				</div>
                			</div>
                    
                			<div class="frame"></div>
                    
                <!--			3-->
                			<div class="frame">
                				<div class="frame__content text-right">
                					<h5>%d раз</h5>
                					<p>%s</p>
                				</div>
                			</div>
                    
                			<div class="frame">
                				<div class="frame__content text-left">
                					<h5>%d раз</h5>
                					<p>%s</p>
                				</div>
                			</div>
                    
                			<div class="frame"></div>
                			<!--			4-->
                			<div class="frame">
                				<div class="frame__content text-right">
                					<h5>%d раз</h5>
                					<p>%s</p>
                				</div>
                			</div>
                    
                			<div class="frame">
                				<div class="frame__content text-left">
                					<h5>%d раз</h5>
                					<p>%s</p>
                				</div>
                			</div>
                    
                			<div class="frame"></div>
                			<!--			5-->
                			<div class="frame">
                				<div class="frame__content text-right">
                					<h5>%d раз</h5>
                					<p>%s</p>
                				</div>
                			</div>
                    
                			<div class="frame">
                				<div class="frame__content text-left">
                					<h5>%d раз</h5>
                					<p>%s</p>
                				</div>
                			</div>
                    
                			<div class="frame"></div>
                			<!--			6-->
                			<div class="frame">
                				<div class="frame__content text-right">
                					<h5>%d раз</h5>
                					<p>%s</p>
                				</div>
                			</div>
                    
                			<div class="frame">
                				<div class="frame__content text-left">
                					<h5>%d раз</h5>
                					<p>%s</p>
                				</div>
                			</div>
                    
                			<div class="frame"></div>
                			<!--			7-->
                			<div class="frame">
                				<div class="frame__content text-right">
                					<h5>%d раз</h5>
                					<p>%s</p>
                				</div>
                			</div>
                    
                			<div class="frame">
                				<div class="frame__content text-left">
                					<h5>%d раз</h5>
                					<p>%s</p>
                				</div>
                			</div>
                    
                			<div class="frame"></div>
                			<!--			8-->
                			<div class="frame">
                				<div class="frame__content text-right">
                					<h5>%d раз</h5>
                					<p>%s</p>
                				</div>
                			</div>
                    
                			<div class="frame">
                				<div class="frame__content text-left">
                					<h5>%d раз</h5>
                					<p>%s</p>
                				</div>
                			</div>
                    
                			<div class="frame"></div>
                			<!--			9-->
                			<div class="frame">
                				<div class="frame__content text-right">
                					<h5>%d раз</h5>
                					<p>%s</p>
                				</div>
                			</div>
                    
                			<div class="frame">
                				<div class="frame__content text-left">
                					<h5>%d раз</h5>
                					<p>%s</p>
                				</div>
                			</div>
                    
                			<div class="frame"></div>
                			<!--			10-->
                			<div class="frame">
                				<div class="frame__content text-right">
                					<h5>%d раз</h5>
                					<p>%s</p>
                				</div>
                			</div>
                    
                			<div class="frame">
                				<div class="frame__content text-left">
                					<h5>%d раз</h5>
                					<p>%s</p>
                				</div>
                			</div>
                    
                			<div class="frame"></div>
                    
                			<div class="frame frame_bg">
                				<div class="frame__content">
                					<video class="frame-media" src="media/video_optimized.mp4" autoplay loop muted></video>
                				</div>
                			</div>
                    
                			<div class="frame"></div>
                			<div class="frame"></div>
                    
                			<div class="frame">
                				<div class="frame__content">With love, glebchik_gg</div>
                			</div>
                    
                		</section>
                	</div>
                    
                	<img class="soundbutton paused" src="images/sound.gif" alt="Alt">
                	<audio class="audio" src="media/home.mp3" loop></audio>
                    
                </body>
                </html>
                    
                """, chat.getName(),
                analyzer.getCountOfMessagesPerDay().size(),
                analyzer.getMessagesCount(),
                (float) analyzer.getMessagesCount() / analyzer.getCountOfMessagesPerDay().size(),
                (float) analyzer.getTotalWords() / analyzer.getCountOfMessagesPerDay().size(),
                names[0],
                analyzer.getCountOfMessagesPerUser().get(names[0]),
                analyzer.getMostPopularWordsPerUser().get(names[0]).values().stream().mapToInt(Integer::intValue).sum(),
                (float) analyzer.getMostPopularWordsPerUser().get(names[0]).values().stream().mapToInt(Integer::intValue).sum() / analyzer.getCountOfMessagesPerUser().get(names[0]),
                names[1],
                analyzer.getCountOfMessagesPerUser().get(names[1]),
                analyzer.getMostPopularWordsPerUser().get(names[1]).values().stream().mapToInt(Integer::intValue).sum(),
                (float) analyzer.getMostPopularWordsPerUser().get(names[1]).values().stream().mapToInt(Integer::intValue).sum() / analyzer.getCountOfMessagesPerUser().get(names[1]),
                names[0],
                names[1],
                topMessages0.get(0).getValue(),
                topMessages0.get(0).getKey(),
                topMessages1.get(0).getValue(),
                topMessages1.get(0).getKey(),
                topMessages0.get(1).getValue(),
                topMessages0.get(1).getKey(),
                topMessages1.get(1).getValue(),
                topMessages1.get(1).getKey(),
                topMessages0.get(2).getValue(),
                topMessages0.get(2).getKey(),
                topMessages1.get(2).getValue(),
                topMessages1.get(2).getKey(),
                topMessages0.get(3).getValue(),
                topMessages0.get(3).getKey(),
                topMessages1.get(3).getValue(),
                topMessages1.get(3).getKey(),
                topMessages0.get(4).getValue(),
                topMessages0.get(4).getKey(),
                topMessages1.get(4).getValue(),
                topMessages1.get(4).getKey(),
                topMessages0.get(5).getValue(),
                topMessages0.get(5).getKey(),
                topMessages1.get(5).getValue(),
                topMessages1.get(5).getKey(),
                topMessages0.get(6).getValue(),
                topMessages0.get(6).getKey(),
                topMessages1.get(6).getValue(),
                topMessages1.get(6).getKey(),
                topMessages0.get(7).getValue(),
                topMessages0.get(7).getKey(),
                topMessages1.get(7).getValue(),
                topMessages1.get(7).getKey(),
                topMessages0.get(8).getValue(),
                topMessages0.get(8).getKey(),
                topMessages1.get(8).getValue(),
                topMessages1.get(8).getKey(),
                topMessages0.get(9).getValue(),
                topMessages0.get(9).getKey(),
                topMessages1.get(9).getValue(),
                topMessages1.get(9).getKey()
        );
        try {
            Files.writeString(Path.of("output/ready-html/index.html"), result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}