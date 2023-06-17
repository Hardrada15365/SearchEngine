INSERT INTO `search_engine`.`sites`
(`id`,
`last_error`,
`name`,
`status`,
`status_time`,
`url`)
VALUES
(120698, 'Ошибок нет', 'Skillbox', 'INDEXED', '2023-05-25 11:49:36', 'https://www.skillbox.ru'),
(120697, 'Ошибок нет', 'Skill', 'INDEXED', '2023-05-25 11:49:36', 'https://www.skillbox.ru'),
(120696, 'Ошибок нет', 'box', 'INDEXED', '2023-05-25 11:49:36', 'https://www.skillbox.ru');

INSERT INTO `search_engine`.`pages`
(`id`,
`code`,
`content`,
`path`,
`site_id`)
VALUES
(120768, '200',  'contont1', 'https://www.playback.ru/basket1.html', 120698),
(120769, '200',  'contont1', 'https://www.playback.ru/basket2.html', 120698),
(120770, '200',  'contont1', 'https://www.playback.ru/basket3.html', 120698),
(120771, '200',  'contont4', 'https://www.playback.ru/basket4.html', 120697),
(120772, '200',  'contont5', 'https://www.playback.ru/basket5.html', 120697),
(120773, '200',  'contont6', 'https://www.playback.ru/basket6.html', 120696);

INSERT INTO `search_engine`.`indexes_`
(`id`,
`rank_`,
`lemma_id`,
`page_id`)
VALUES
(490181, '1', 1242771, 120768),
(490182, '1', 1242771, 120768),
(490183, '1', 1242773, 120769),
(490184, '1', 1242773, 120771),
(490185, '1', 1242777, 120772);

INSERT INTO `search_engine`.`lemmas`
(`id`,
`frequency`,
`lemma`,
`site_id`)
VALUES
(1242771, '1', 'сообщение', 120698),
(1242772, '1', 'сообщение', 120698),
(1242773, '1', 'сообщение', 120698),
(1242774, '1', 'сообщение', 120697),
(1242775, '1', 'сообщение', 120696),
(1242776, '1', 'сообщение', 120696),
(1242777, '1', 'сообщение', 120697);