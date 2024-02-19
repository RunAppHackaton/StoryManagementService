-- Inserting into Story table with ID specified
INSERT INTO Story (id, tittle, description, category, story_image_url, difficult_level)
VALUES
    (1, 'Adventurous Running Journey', 'Embark on an exciting running adventure with our app!', 'Adventure','https://storage.cloud.google.com/run-app-bucket/story-images/light-run.jpg?authuser=3', 1),
    (2, 'Mountain Trail Challenge', 'Conquer rugged mountain trails and push your limits.', 'Adventure','https://storage.cloud.google.com/run-app-bucket/story-images/mountain.jpg?authuser=3', 4),
    (3, 'Urban Explorer Marathon', 'Navigate through city streets and discover urban landscapes.', 'Adventure','https://storage.cloud.google.com/run-app-bucket/story-images/marathon.jpg?authuser=3', 2),
    (4, 'Coastal Running Adventure', 'Experience the beauty of coastal paths while staying active.', 'Adventure','https://storage.cloud.google.com/run-app-bucket/story-images/beach.jpg?authuser=3', 3),
    (5, 'Forest Trail Expedition', 'Venture deep into the forest and run amidst nature.', 'Adventure','https://storage.cloud.google.com/run-app-bucket/story-images/jungles.jpg?authuser=3', 4),
    (6, 'Desert Dash Adventure', 'Run across vast deserts and enjoy the serenity of the wilderness.', 'Adventure','https://storage.cloud.google.com/run-app-bucket/story-images/desert.jpg?authuser=3', 5),
    (7, 'Island Escape Run', 'Explore remote islands and run along pristine beaches.', 'Adventure','https://storage.cloud.google.com/run-app-bucket/story-images/beach.jpg?authuser=3', 3),
    (8, 'Jungle Trek Challenge', 'Navigate through dense jungles and encounter exotic wildlife.', 'Adventure','https://storage.cloud.google.com/run-app-bucket/story-images/jungles.jpg?authuser=3', 4),
    (9, 'Arctic Expedition Run', 'Embark on a chilly adventure and run in icy landscapes.', 'Adventure','https://storage.cloud.google.com/run-app-bucket/story-images/arctic.jpg?authuser=3', 5),
    (10, 'Historic City Run', 'Discover the rich history of ancient cities while staying active.', 'Adventure','https://storage.cloud.google.com/run-app-bucket/story-images/urban.jpg?authuser=3', 2),
    (11, 'Trailblazer Marathon', 'Blaze new trails and conquer challenging terrains.', 'Adventure','https://storage.cloud.google.com/run-app-bucket/story-images/marathon.jpg?authuser=3', 4),
    (12, 'Skyline Challenge', 'Run along skyscraper-lined streets and enjoy breathtaking city views.', 'Adventure','https://storage.cloud.google.com/run-app-bucket/story-images/urban.jpg?authuser=3', 3),
    (13, 'Sunset Beach Run', 'Experience the beauty of sunsets while running on tranquil beaches.', 'Adventure','https://storage.cloud.google.com/run-app-bucket/story-images/beach.jpg?authuser=3', 2),
    (14, 'Wilderness Wanderlust Run', 'Wander through untamed wilderness and immerse yourself in nature.', 'Adventure','https://storage.cloud.google.com/run-app-bucket/story-images/jungles.jpg?authuser=3', 4),
    (15, 'Mountain Peak Pursuit', 'Reach new heights and conquer towering mountain peaks.', 'Adventure','https://storage.cloud.google.com/run-app-bucket/story-images/mountain.jpg?authuser=3', 5);

-- Inserting into Task table for each story
INSERT INTO Task (id, task_title, task_description, task_reward_id, task_image_url)
VALUES
-- Tasks for Story with ID 1
(1,'Easy Run', 'Run 2 miles at a comfortable pace', 1, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(2,'Interval Training', 'Run 1 mile, alternating between 1 minute of hard running and 2 minutes of easy jogging for a total of 4 intervals', 2, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(3,'Run uphill for 2 miles', 'Challenge yourself with uphill running to build endurance.', 3, 'https://storage.cloud.google.com/run-app-bucket/task-images/uphill.jpg?authuser=3'),
-- Tasks for Story with ID 2
(4,'Hill Repeats', 'Run 0.5 miles uphill, jog downhill, repeat for a total of 4 repetitions.', 1, 'https://storage.cloud.google.com/run-app-bucket/task-images/uphill.jpg?authuser=3'),
(5,'Trail run with at least 1000ft elevation gain', 'Tackle steep ascents and descents on rugged trails.', 2, 'https://storage.cloud.google.com/run-app-bucket/task-images/uphill.jpg?authuser=3'),
(6,'Complete a trail run longer than 10 miles', 'Endure long-distance running on challenging terrain.', 3, 'https://storage.cloud.google.com/run-app-bucket/task-images/uphill.jpg?authuser=3'),
-- Tasks for Story with ID 3
(7,'Run 2 miles at a comfortable pace', 'Navigate through city streets to find hidden checkpoints.', 1, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(8,'Interval Training', 'Run 1 mile, alternating between 1 minute of hard running and 2 minutes of easy jogging for a total of 4 intervals.', 2, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(9,'Run 3 miles at a steady, conversational pace', 'Cover a long distance while experiencing the vibrancy of urban life.', 3, 'https://storage.cloud.google.com/run-app-bucket/task-images/marathon.jpg?authuser=3'),
-- Tasks for Story with ID 4
(10,'Run 3 miles at a comfortable pace', 'Enjoy scenic views of the ocean while running on coastal paths.', 1, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(11,'Run 1.5 miles at a moderately hard pace', 'Challenge yourself with running on sandy terrain.', 2, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(12,'Run 3 miles at a comfortable pace', 'Engage in high-intensity interval training on the beach.', 3, 'https://storage.cloud.google.com/run-app-bucket/task-images/sprint.jpg?authuser=3'),
-- Tasks for Story with ID 5
(13,'Sprint Challenge', 'Complete 6 x 200-meter sprints with 1-minute rest between each sprint.', 1, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(14,'Run 3 miles at an easy pace', 'Experience the thrill of crossing natural water obstacles.', 2, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(15,'Interval Training', 'Run 2 miles, alternating between 3 minutes of hard running and 2 minutes of easy jogging for a total of 4 intervals.', 3, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
-- Tasks for Story with ID 6
(16,'Run 5 miles at a steady, conversational pace', 'Experience the vastness of the desert while running on sandy terrain.', 1, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(17,'Complete a desert trail run longer than 15 miles', 'Endure long-distance running under the scorching desert sun.', 2, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(18,'Conquer a sand dune challenge', 'Climb to the top of a towering sand dune for an exhilarating view.', 3, 'https://storage.cloud.google.com/run-app-bucket/task-images/uphill.jpg?authuser=3'),
-- Tasks for Story with ID 7
(19,'Run 2.5 miles at a moderately hard pace', 'Explore different islands and enjoy coastal running adventures.', 1, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(20,'Sprint Challenge', 'Complete 10 x 200-meter sprints with 1-minute rest between each sprint.', 2, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(21,'Complete a multi-island marathon', 'Challenge yourself with a long-distance run spanning multiple islands.', 3, 'https://storage.cloud.google.com/run-app-bucket/task-images/marathon.jpg?authuser=3'),
-- Tasks for Story with ID 8
(22,'Navigate through dense jungle trails', 'Immerse yourself in the sights and sounds of the jungle while running.', 1, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(23,'Spot exotic wildlife during your jungle run', 'Keep an eye out for animals like monkeys, birds, and reptiles.', 2, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(24,'Complete a jungle obstacle course run', 'Test your agility and endurance on a jungle-themed obstacle course.', 3, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
-- Tasks for Story with ID 9
(25,'Run amidst icy landscapes and frozen lakes', 'Experience the serene beauty of the Arctic while staying active.', 1, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(26,'Complete an Arctic trail run under the Northern Lights', 'Run under the mesmerizing glow of the Aurora Borealis.', 2, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(27,'Survive sub-zero temperatures during your Arctic run', 'Brave the cold and push your limits in extreme conditions.', 3, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
-- Tasks for Story with ID 10
(28,'Explore ancient city ruins on your run', 'Discover the remnants of past civilizations while staying active.', 1, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(29,'Complete a city-wide treasure hunt run', 'Search for hidden artifacts and treasures scattered throughout the city.', 2, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(30,'Run past historical landmarks and monuments', 'Learn about the rich history of the city as you run.', 3, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
-- Tasks for Story with ID 11
(31,'Blaze new trails through uncharted territory', 'Explore unexplored regions and leave your mark on the trail.', 1, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(32,'Conquer challenging terrains and rugged landscapes', 'Push your limits and overcome obstacles on your trail run.', 2, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(33,'Complete a marathon on difficult terrain', 'Endure a long-distance run on tough trails and earn bragging rights.', 3, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
-- Tasks for Story with ID 12
(34,'Run along skyscraper-lined streets', 'Experience the hustle and bustle of the city while running amidst towering buildings.', 1, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(35,'Complete a city-wide relay race', 'Team up with friends for an exciting relay race through the city streets.', 2, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(36,'Discover hidden rooftop running tracks', 'Explore elevated running routes with panoramic views of the city skyline.', 3, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
-- Tasks for Story with ID 13
(37,'Run along the beach during sunset', 'Enjoy the tranquility of the beach and the beauty of the sunset while running.', 1, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(38,'Complete a beachside yoga session after your run', 'Unwind and stretch out tired muscles with a relaxing yoga session.', 2, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(39,'Collect seashells and beach treasures during your run', 'Search for souvenirs and mementos along the shoreline.', 3, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
-- Tasks for Story with ID 14
(40,'Wander through untouched wilderness', 'Escape the hustle and bustle of civilization and reconnect with nature.', 1, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(41,'Camp under the stars after your wilderness run', 'Spend a night in the great outdoors and enjoy the tranquility of nature.', 2, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
(42,'Spot rare flora and fauna during your wilderness run', 'Keep an eye out for rare plants and animals in their natural habitat.', 3, 'https://storage.cloud.google.com/run-app-bucket/task-images/slow.jpg?authuser=3'),
-- Tasks for Story with ID 15
(43,'Reach the summit of a towering mountain peak', 'Conquer a challenging ascent and enjoy breathtaking views from the top.', 1, 'https://storage.cloud.google.com/run-app-bucket/task-images/uphill.jpg?authuser=3'),
(44,'Complete a multi-day mountain trek', 'Embark on an epic journey and traverse rugged mountain terrain over several days.', 2, 'https://storage.cloud.google.com/run-app-bucket/task-images/uphill.jpg?authuser=3'),
(45,'Encounter mountain wildlife during your trek', 'Spot elusive animals like mountain goats, eagles, and marmots.', 3, 'https://storage.cloud.google.com/run-app-bucket/task-images/uphill.jpg?authuser=3');
INSERT INTO Task_Story (task_id, story_id) VALUES
                                               (1, 1), (2, 1), (3, 1),
                                               (4, 2), (5, 2), (6, 2),
                                               (7, 3), (8, 3), (9, 3),
                                               (10, 4), (11, 4), (12, 4),
                                               (13, 5), (14, 5), (15, 5),
                                               (16, 6), (17, 6), (18, 6),
                                               (19, 7), (20, 7), (21, 7),
                                               (22, 8), (23, 8), (24, 8),
                                               (25, 9), (26, 9), (27, 9),
                                               (28, 10), (29, 10), (30, 10),
                                               (31, 11), (32, 11), (33, 11),
                                               (34, 12), (35, 12), (36, 12),
                                               (37, 13), (38, 13), (39, 13),
                                               (40, 14), (41, 14), (42, 14),
                                               (43, 15), (44, 15), (45, 15);