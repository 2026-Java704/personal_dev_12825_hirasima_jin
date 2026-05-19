-- ユーザーテーブルデータ
INSERT INTO users(name, email, password) VALUES('田中太郎', 'tanaka@aaa.com', 'test123');
INSERT INTO users(name, email, password) VALUES('鈴木一郎', 'suzuki@aaa.com', 'test456');

-- カテゴリーテーブル --
INSERT INTO categories(name) VALUES('丼もの');
INSERT INTO categories(name) VALUES('麵類');
INSERT INTO categories(name) VALUES('デザート');
INSERT INTO categories(name) VALUES('肉料理');
INSERT INTO categories(name) VALUES('スープ');
INSERT INTO categories(name) VALUES('サラダ');
INSERT INTO categories(name) VALUES('魚介');
INSERT INTO categories(name) VALUES('パン');
INSERT INTO categories(name) VALUES('鍋もの');
INSERT INTO categories(name) VALUES('粉もの');

---- レシピテーブル --
INSERT INTO recipes(user_id, category_id, name, recipe) VALUES(1, 1, '親子丼', '親子丼の作り方');
INSERT INTO recipes(user_id, category_id, name, recipe) VALUES(1, 3, 'マカロン', 'マカロンの作り方');
INSERT INTO recipes(user_id, category_id, name, recipe) VALUES(2, 2, 'タラコパスタ', 'タラコパスタの作り方');