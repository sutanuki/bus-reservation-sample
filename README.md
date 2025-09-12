# プロジェクトの概要
- プロジェクト名

     **bus-reservation-sample**
- 開発者

  **山下 歩**

- 機能一覧
  - **ゲストユーザー**
    - 詳細画面の表示
    - 高速バスの予約(バスのid,名前,電話番号)
    - キャンセル
    - ログイン(企業,サイト管理者)
  
  - **企業**
    - 自社のバスを一覧表示
    - 新規のバス登録(出発場所,到着場所,出発日時,到着日時,最終受付日時,席数)
    - ログアウト
  
  - **サイト管理者**
    - 企業アカウントの管理(一覧表示,追加,削除)
    - 全社のバスの管理(一覧表示,削除)
    - 全予約の管理(一覧表示,キャンセル)
    - ログアウト

# ディレクトリ構成
```
.
├── data
│   ├── companies.json
│   ├── reservations.json
│   └── trips.json
├── pom.xml
└── src
      └── main
        ├── java
        │   └── com
        │       └── example
        │           └── bus
        │               ├── dao
        │               │   ├── BusCompanyDAO.java
        │               │   ├── BusReservationDAO.java
        │               │   └── BusTripDAO.java
        │               ├── model
        │               │   ├── BusCompany.java
        │               │   ├── BusReservation.java
        │               │   ├── BusTrip.java
        │               │   ├── BusTripDto.java
        │               │   └── UserRole.java
        │               ├── service
        │               │   └── AuthService.java
        │               ├── util
        │               │   ├── DateUtils.java
        │               │   └── ReservationCode.java
        │               └── web
        │                   ├── AdminServlet.java
        │                   ├── AuthServlet.java
        │                   ├── CancelServlet.java
        │                   ├── CompanyServlet.java
        │                   ├── DetailServlet.java
        │                   ├── ReservationServlet.java
        │                   ├── SearchServlet.java
        │                   └── TopServlet.java
        ├── resources
        └── webapp
            ├── WEB-INF
            │   └── web.xml
            ├── admin
            │   └── index.jsp
            ├── assets
            │   └── style.css
            ├── auth
            │   └── login.jsp
            ├── cancel.jsp
            ├── company
            │   └── index.jsp
            ├── completed.jsp
            ├── detail.jsp
            ├── include
            │   └── header.jspf
            ├── reservation.jsp
            └── search.jsp
            └── top.jsp
```
# 機能詳細
- **ゲストユーザー**
  - 詳細画面の表示
    ```
    
    ```
  - 高速バスの予約(バスのid,名前,電話番号)
    ```
    
    ```
  - キャンセル
    ```
    
    ```
  - ログイン(企業,サイト管理者)
    ```
    
    ```

- **企業**
  - 自社のバスを一覧表示
    ```
    
    ```
  - 新規のバス登録(出発場所,到着場所,出発日時,到着日時,最終受付日時,席数)
    ```
    
    ```
  - ログアウト
    ```
    
    ```

- **サイト管理者**
  - 企業アカウントの管理(一覧表示,追加,削除)
    ```
    
    ```
  - 全社のバスの管理(一覧表示,削除)
    ```
    
    ```
  - 全予約の管理(一覧表示,キャンセル)
    ```
    
    ```
  - ログアウト
    ```
    
    ```
# 起動について
- 前提条件
  - Java 17 (JDK)
  - Apache Maven 3.6+
  - Servletコンテナ
  - IDE ※任意

- ビルド手順

  プロジェクトのルートディレクトリで以下を実行し、WARファイルをビルド
  ```
  mvn clean package
  ```

  target/フォルダにbus-reservation-sample.warが生成されます。

- デプロイ方法

  ビルド後、target/bus-reservation-sample.warを取得

  WARファイルをTomcatのwebapps/ディレクトリに配置

  Tomcatを起動
  ```
  cd /path/to/tomcat
  ./bin/startup.sh    # Linux / macOS
  ```
  または
  ```
  ./bin/startup.bat   # Windows
  ```

  ブラウザで以下にアクセス
  ```
  http://localhost:8080/bus-reservation-sample/
  ```
