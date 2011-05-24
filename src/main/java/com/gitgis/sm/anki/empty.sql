PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;
CREATE TABLE "deckVars" (
	"key" TEXT NOT NULL, 
	value TEXT, 
	PRIMARY KEY ("key")
);
INSERT INTO "deckVars" VALUES('suspendLeeches','1');
INSERT INTO "deckVars" VALUES('leechFails','16');
INSERT INTO "deckVars" VALUES('cssCache','#cmqb2dde3301de8d91c {font-family:"Arial";font-size:20px;color:#000000;text-align:center;}
#cmqf9bfa9301de8d91b {font-family:"Arial";font-size:20px;color:#000000;text-align:center;}
#cmab2dde3301de8d91c {font-family:"Arial";font-size:20px;color:#000000;text-align:center;}
#cmaf9bfa9301de8d91b {font-family:"Arial";font-size:20px;color:#000000;text-align:center;}
.cmbb2dde3301de8d91c {background:#FFFFFF;}
.cmbf9bfa9301de8d91b {background:#FFFFFF;}
');
INSERT INTO "deckVars" VALUES('hexCache','{"-450455413588436709": "f9bfa9301de8d91b", "-549677541902198501": "f85f27301de8d91b", "-5558036569305589476": "b2dde3301de8d91c", "7752868899852966171": "6b97b9301de8d91b", "5178503160903817499": "47ddbd301de8d91b"}');
INSERT INTO "deckVars" VALUES('pageSize','4096');
INSERT INTO "deckVars" VALUES('sortIndex','0');
CREATE TABLE "reviewHistory" (
	"cardId" INTEGER NOT NULL, 
	time FLOAT NOT NULL, 
	"lastInterval" FLOAT NOT NULL, 
	"nextInterval" FLOAT NOT NULL, 
	ease INTEGER NOT NULL, 
	delay FLOAT NOT NULL, 
	"lastFactor" FLOAT NOT NULL, 
	"nextFactor" FLOAT NOT NULL, 
	reps FLOAT NOT NULL, 
	"thinkingTime" FLOAT NOT NULL, 
	"yesCount" FLOAT NOT NULL, 
	"noCount" FLOAT NOT NULL, 
	PRIMARY KEY ("cardId", time)
);
CREATE TABLE sources (
	id INTEGER NOT NULL, 
	name TEXT NOT NULL, 
	created FLOAT NOT NULL, 
	"lastSync" FLOAT NOT NULL, 
	"syncPeriod" INTEGER NOT NULL, 
	PRIMARY KEY (id)
);
CREATE TABLE media (
	id INTEGER NOT NULL, 
	filename TEXT NOT NULL, 
	size INTEGER NOT NULL, 
	created FLOAT NOT NULL, 
	"originalPath" TEXT NOT NULL, 
	description TEXT NOT NULL, 
	PRIMARY KEY (id)
);
CREATE TABLE stats (
	id INTEGER NOT NULL, 
	type INTEGER NOT NULL, 
	day DATE NOT NULL, 
	reps INTEGER NOT NULL, 
	"averageTime" FLOAT NOT NULL, 
	"reviewTime" FLOAT NOT NULL, 
	"distractedTime" FLOAT NOT NULL, 
	"distractedReps" INTEGER NOT NULL, 
	"newEase0" INTEGER NOT NULL, 
	"newEase1" INTEGER NOT NULL, 
	"newEase2" INTEGER NOT NULL, 
	"newEase3" INTEGER NOT NULL, 
	"newEase4" INTEGER NOT NULL, 
	"youngEase0" INTEGER NOT NULL, 
	"youngEase1" INTEGER NOT NULL, 
	"youngEase2" INTEGER NOT NULL, 
	"youngEase3" INTEGER NOT NULL, 
	"youngEase4" INTEGER NOT NULL, 
	"matureEase0" INTEGER NOT NULL, 
	"matureEase1" INTEGER NOT NULL, 
	"matureEase2" INTEGER NOT NULL, 
	"matureEase3" INTEGER NOT NULL, 
	"matureEase4" INTEGER NOT NULL, 
	PRIMARY KEY (id)
);
CREATE TABLE models (
	id INTEGER NOT NULL, 
	"deckId" INTEGER, 
	created FLOAT NOT NULL, 
	modified FLOAT NOT NULL, 
	tags TEXT NOT NULL, 
	name TEXT NOT NULL, 
	description TEXT NOT NULL, 
	features TEXT NOT NULL, 
	spacing FLOAT NOT NULL, 
	"initialSpacing" FLOAT NOT NULL, 
	source INTEGER NOT NULL, 
	PRIMARY KEY (id)
);
INSERT INTO "models" VALUES(5178503160903817499,1,1306171857.18677,1306171857.18677,'Basic','Podstawowy','','',0.1,60.0,0);
CREATE TABLE "fieldModels" (
	id INTEGER NOT NULL, 
	ordinal INTEGER NOT NULL, 
	"modelId" INTEGER NOT NULL, 
	name TEXT NOT NULL, 
	description TEXT NOT NULL, 
	features TEXT NOT NULL, 
	required BOOLEAN NOT NULL, 
	"unique" BOOLEAN NOT NULL, 
	numeric BOOLEAN NOT NULL, 
	"quizFontFamily" TEXT, 
	"quizFontSize" INTEGER, 
	"quizFontColour" VARCHAR(7), 
	"editFontFamily" TEXT, 
	"editFontSize" INTEGER, 
	PRIMARY KEY (id), 
	CHECK (numeric IN (0, 1)), 
	CHECK (required IN (0, 1)), 
	CHECK ("unique" IN (0, 1)), 
	FOREIGN KEY("modelId") REFERENCES models (id)
);
INSERT INTO "fieldModels" VALUES(-549677541902198501,1,5178503160903817499,'Back','','',0,0,0,NULL,NULL,NULL,NULL,20);
INSERT INTO "fieldModels" VALUES(7752868899852966171,0,5178503160903817499,'Front','','',1,1,0,NULL,NULL,NULL,NULL,20);
CREATE TABLE "cardModels" (
	id INTEGER NOT NULL, 
	ordinal INTEGER NOT NULL, 
	"modelId" INTEGER NOT NULL, 
	name TEXT NOT NULL, 
	description TEXT NOT NULL, 
	active BOOLEAN NOT NULL, 
	qformat TEXT NOT NULL, 
	aformat TEXT NOT NULL, 
	lformat TEXT, 
	qedformat TEXT, 
	aedformat TEXT, 
	"questionInAnswer" BOOLEAN NOT NULL, 
	"questionFontFamily" TEXT, 
	"questionFontSize" INTEGER, 
	"questionFontColour" VARCHAR(7), 
	"questionAlign" INTEGER, 
	"answerFontFamily" TEXT, 
	"answerFontSize" INTEGER, 
	"answerFontColour" VARCHAR(7), 
	"answerAlign" INTEGER, 
	"lastFontFamily" TEXT, 
	"lastFontSize" INTEGER, 
	"lastFontColour" VARCHAR(7), 
	"editQuestionFontFamily" TEXT, 
	"editQuestionFontSize" INTEGER, 
	"editAnswerFontFamily" TEXT, 
	"editAnswerFontSize" INTEGER, 
	"allowEmptyAnswer" BOOLEAN NOT NULL, 
	"typeAnswer" TEXT NOT NULL, 
	PRIMARY KEY (id), 
	FOREIGN KEY("modelId") REFERENCES models (id), 
	CHECK ("questionInAnswer" IN (0, 1)), 
	CHECK ("allowEmptyAnswer" IN (0, 1)), 
	CHECK (active IN (0, 1))
);
INSERT INTO "cardModels" VALUES(-5558036569305589476,1,5178503160903817499,'Reverse','',0,'%(Back)s','%(Front)s',NULL,NULL,NULL,0,'Arial',20,'#000000',0,'Arial',20,'#000000',0,'Arial',20,'#FFFFFF',NULL,NULL,NULL,NULL,1,'');
INSERT INTO "cardModels" VALUES(-450455413588436709,0,5178503160903817499,'Forward','',1,'%(Front)s','%(Back)s',NULL,NULL,NULL,0,'Arial',20,'#000000',0,'Arial',20,'#000000',0,'Arial',20,'#FFFFFF',NULL,NULL,NULL,NULL,1,'');
CREATE TABLE decks (
	id INTEGER NOT NULL, 
	created FLOAT NOT NULL, 
	modified FLOAT NOT NULL, 
	description TEXT NOT NULL, 
	version INTEGER NOT NULL, 
	"currentModelId" INTEGER, 
	"syncName" TEXT, 
	"lastSync" FLOAT NOT NULL, 
	"hardIntervalMin" FLOAT NOT NULL, 
	"hardIntervalMax" FLOAT NOT NULL, 
	"midIntervalMin" FLOAT NOT NULL, 
	"midIntervalMax" FLOAT NOT NULL, 
	"easyIntervalMin" FLOAT NOT NULL, 
	"easyIntervalMax" FLOAT NOT NULL, 
	delay0 INTEGER NOT NULL, 
	delay1 INTEGER NOT NULL, 
	delay2 FLOAT NOT NULL, 
	"collapseTime" INTEGER NOT NULL, 
	"highPriority" TEXT NOT NULL, 
	"medPriority" TEXT NOT NULL, 
	"lowPriority" TEXT NOT NULL, 
	suspended TEXT NOT NULL, 
	"newCardOrder" INTEGER NOT NULL, 
	"newCardSpacing" INTEGER NOT NULL, 
	"failedCardMax" INTEGER NOT NULL, 
	"newCardsPerDay" INTEGER NOT NULL, 
	"sessionRepLimit" INTEGER NOT NULL, 
	"sessionTimeLimit" INTEGER NOT NULL, 
	"utcOffset" FLOAT NOT NULL, 
	"cardCount" INTEGER NOT NULL, 
	"factCount" INTEGER NOT NULL, 
	"failedNowCount" INTEGER NOT NULL, 
	"failedSoonCount" INTEGER NOT NULL, 
	"revCount" INTEGER NOT NULL, 
	"newCount" INTEGER NOT NULL, 
	"revCardOrder" INTEGER NOT NULL, 
	PRIMARY KEY (id), 
	FOREIGN KEY("currentModelId") REFERENCES models (id)
);
INSERT INTO "decks" VALUES(1,1306171857.12331,1306171901.47861,'',43,5178503160903817499,NULL,0.0,0.333,0.5,3.0,5.0,7.0,9.0,600,600,0.0,1,'PriorityVeryHigh','PriorityHigh','PriorityLow','',1,0,20,20,0,600,10800.0,0,0,0,0,0,0,0);
CREATE TABLE "modelsDeleted" (
	"modelId" INTEGER NOT NULL, 
	"deletedTime" FLOAT NOT NULL, 
	FOREIGN KEY("modelId") REFERENCES models (id)
);
CREATE TABLE facts (
	id INTEGER NOT NULL, 
	"modelId" INTEGER NOT NULL, 
	created FLOAT NOT NULL, 
	modified FLOAT NOT NULL, 
	tags TEXT NOT NULL, 
	"spaceUntil" FLOAT NOT NULL, 
	"lastCardId" INTEGER, 
	PRIMARY KEY (id), 
	FOREIGN KEY("modelId") REFERENCES models (id)
);
CREATE TABLE cards (
	id INTEGER NOT NULL, 
	"factId" INTEGER NOT NULL, 
	"cardModelId" INTEGER NOT NULL, 
	created FLOAT NOT NULL, 
	modified FLOAT NOT NULL, 
	tags TEXT NOT NULL, 
	ordinal INTEGER NOT NULL, 
	question TEXT NOT NULL, 
	answer TEXT NOT NULL, 
	priority INTEGER NOT NULL, 
	interval FLOAT NOT NULL, 
	"lastInterval" FLOAT NOT NULL, 
	due FLOAT NOT NULL, 
	"lastDue" FLOAT NOT NULL, 
	factor FLOAT NOT NULL, 
	"lastFactor" FLOAT NOT NULL, 
	"firstAnswered" FLOAT NOT NULL, 
	reps INTEGER NOT NULL, 
	successive INTEGER NOT NULL, 
	"averageTime" FLOAT NOT NULL, 
	"reviewTime" FLOAT NOT NULL, 
	"youngEase0" INTEGER NOT NULL, 
	"youngEase1" INTEGER NOT NULL, 
	"youngEase2" INTEGER NOT NULL, 
	"youngEase3" INTEGER NOT NULL, 
	"youngEase4" INTEGER NOT NULL, 
	"matureEase0" INTEGER NOT NULL, 
	"matureEase1" INTEGER NOT NULL, 
	"matureEase2" INTEGER NOT NULL, 
	"matureEase3" INTEGER NOT NULL, 
	"matureEase4" INTEGER NOT NULL, 
	"yesCount" INTEGER NOT NULL, 
	"noCount" INTEGER NOT NULL, 
	"spaceUntil" FLOAT NOT NULL, 
	"relativeDelay" FLOAT NOT NULL, 
	"isDue" BOOLEAN NOT NULL, 
	type INTEGER NOT NULL, 
	"combinedDue" INTEGER NOT NULL, 
	PRIMARY KEY (id), 
	CHECK ("isDue" IN (0, 1)), 
	FOREIGN KEY("cardModelId") REFERENCES "cardModels" (id), 
	FOREIGN KEY("factId") REFERENCES facts (id)
);
CREATE TABLE fields (
	id INTEGER NOT NULL, 
	"factId" INTEGER NOT NULL, 
	"fieldModelId" INTEGER NOT NULL, 
	ordinal INTEGER NOT NULL, 
	value TEXT NOT NULL, 
	PRIMARY KEY (id), 
	FOREIGN KEY("fieldModelId") REFERENCES "fieldModels" (id), 
	FOREIGN KEY("factId") REFERENCES facts (id)
);
CREATE TABLE "factsDeleted" (
	"factId" INTEGER NOT NULL, 
	"deletedTime" FLOAT NOT NULL, 
	FOREIGN KEY("factId") REFERENCES facts (id)
);
CREATE TABLE "cardsDeleted" (
	"cardId" INTEGER NOT NULL, 
	"deletedTime" FLOAT NOT NULL, 
	FOREIGN KEY("cardId") REFERENCES cards (id)
);
CREATE TABLE "mediaDeleted" (
	"mediaId" INTEGER NOT NULL, 
	"deletedTime" FLOAT NOT NULL, 
	FOREIGN KEY("mediaId") REFERENCES cards (id)
);
CREATE TABLE tags (
id integer not null,
tag text not null collate nocase,
priority integer not null default 2,
primary key(id));
INSERT INTO "tags" VALUES(1,'PriorityLow',1);
INSERT INTO "tags" VALUES(2,'PriorityHigh',3);
INSERT INTO "tags" VALUES(3,'PriorityVeryHigh',4);
CREATE TABLE cardTags (
id integer not null,
cardId integer not null,
tagId integer not null,
src integer not null,
primary key(id));
ANALYZE sqlite_master;
INSERT INTO "sqlite_stat1" VALUES('tags','ix_tags_tag','5 1');
INSERT INTO "sqlite_stat1" VALUES('cardsDeleted',NULL,'0');
INSERT INTO "sqlite_stat1" VALUES('cards','ix_cards_sort','1 1');
INSERT INTO "sqlite_stat1" VALUES('cards','ix_cards_dueAsc','1 1 1 1 1');
INSERT INTO "sqlite_stat1" VALUES('cards','ix_cards_intervalDesc','1 1 1 1 1');
INSERT INTO "sqlite_stat1" VALUES('cards','ix_cards_factId','1 1 1');
INSERT INTO "sqlite_stat1" VALUES('cards','ix_cards_factor','1 1 1');
INSERT INTO "sqlite_stat1" VALUES('cards','ix_cards_priorityDue','1 1 1 1 1');
INSERT INTO "sqlite_stat1" VALUES('cards','ix_cards_duePriority','1 1 1 1 1');
INSERT INTO "sqlite_stat1" VALUES('facts',NULL,'1');
INSERT INTO "sqlite_stat1" VALUES('fields','ix_fields_value','2 1');
INSERT INTO "sqlite_stat1" VALUES('fields','ix_fields_fieldModelId','2 1');
INSERT INTO "sqlite_stat1" VALUES('fields','ix_fields_factId','2 2');
INSERT INTO "sqlite_stat1" VALUES('modelsDeleted',NULL,'0');
INSERT INTO "sqlite_stat1" VALUES('factsDeleted',NULL,'0');
INSERT INTO "sqlite_stat1" VALUES('decks',NULL,'1');
INSERT INTO "sqlite_stat1" VALUES('mediaDeleted',NULL,'0');
INSERT INTO "sqlite_stat1" VALUES('deckVars','sqlite_autoindex_deckVars_1','5 1');
INSERT INTO "sqlite_stat1" VALUES('sources',NULL,'0');
INSERT INTO "sqlite_stat1" VALUES('cardTags','ix_cardTags_cardId','2 2');
INSERT INTO "sqlite_stat1" VALUES('cardTags','ix_cardTags_tagCard','2 1 1');
INSERT INTO "sqlite_stat1" VALUES('stats','ix_stats_typeDay','2 1 1');
INSERT INTO "sqlite_stat1" VALUES('reviewHistory',NULL,'0');
INSERT INTO "sqlite_stat1" VALUES('models',NULL,'1');
INSERT INTO "sqlite_stat1" VALUES('fieldModels',NULL,'2');
INSERT INTO "sqlite_stat1" VALUES('media',NULL,'0');
INSERT INTO "sqlite_stat1" VALUES('cardModels',NULL,'2');
CREATE INDEX ix_cards_duePriority on cards
(type, isDue, combinedDue, priority);
CREATE INDEX ix_cards_priorityDue on cards
(type, isDue, priority, combinedDue);
CREATE INDEX ix_cards_factor on cards
(type, factor);
CREATE INDEX ix_cards_factId on cards (factId, type);
CREATE INDEX ix_stats_typeDay on stats (type, day);
CREATE INDEX ix_fields_factId on fields (factId);
CREATE INDEX ix_fields_fieldModelId on fields (fieldModelId);
CREATE INDEX ix_fields_value on fields (value);
CREATE INDEX ix_media_originalPath on media (originalPath);
CREATE INDEX ix_cardsDeleted_cardId on cardsDeleted (cardId);
CREATE INDEX ix_modelsDeleted_modelId on modelsDeleted (modelId);
CREATE INDEX ix_factsDeleted_factId on factsDeleted (factId);
CREATE INDEX ix_mediaDeleted_factId on mediaDeleted (mediaId);
CREATE INDEX ix_cardTags_tagCard on cardTags (tagId, cardId);
CREATE INDEX ix_cardTags_cardId on cardTags (cardId);
CREATE INDEX ix_cards_intervalDesc on cards (type, isDue, priority desc, interval desc);
CREATE INDEX ix_cards_dueAsc on cards (type, isDue, priority desc, due);
CREATE UNIQUE INDEX ix_media_filename on media (filename);
CREATE UNIQUE INDEX ix_tags_tag on tags (tag);
CREATE VIEW failedCards as
select * from cards
where type = 0 and isDue = 1
order by type, isDue, combinedDue;
CREATE VIEW revCardsOld as
select * from cards
where type = 1 and isDue = 1
order by priority desc, interval desc;
CREATE VIEW revCardsNew as
select * from cards
where type = 1 and isDue = 1
order by priority desc, interval;
CREATE VIEW revCardsDue as
select * from cards
where type = 1 and isDue = 1
order by priority desc, due;
CREATE VIEW revCardsRandom as
select * from cards
where type = 1 and isDue = 1
order by priority desc, factId, ordinal;
CREATE VIEW acqCardsOld as
select * from cards
where type = 2 and isDue = 1
order by priority desc, due;
CREATE VIEW acqCardsNew as
select * from cards
where type = 2 and isDue = 1
order by priority desc, due desc;
CREATE INDEX ix_cards_sort on cards (question collate nocase);
COMMIT;
