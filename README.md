# 目次
# 1.プロジェクトの概要
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

# 2.ディレクトリ構成
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
# 3.機能詳細
- **ゲストユーザー**
  - 高速バスの検索
    ```
    発着場所や日付,昼行夜行でバスの検索を行えます。
    ```
    <img width="1180" height="647" alt="スクリーンショット 2025-09-12 11 53 09" src="https://github.com/user-attachments/assets/8d8dcd9d-a4ab-443c-8ba2-3f90f4ee56aa" />
  - 詳細画面の表示
    ```
    バスの詳細が表示できます。
    今後企業からのアピールポイントやコンセントの有無などを
    表示できるようにする予定です。
    ```
    <img width="1180" height="643" alt="スクリーンショット 2025-09-12 12 10 32" src="https://github.com/user-attachments/assets/7ce404a5-d3d7-4808-b6aa-dd356b2b649d" />
  - 高速バスの予約
    ```
    名前と電話番号を入力することで予約が可能です。
    ```
    <img width="1180" height="596" alt="スクリーンショット 2025-09-12 12 14 39" src="https://github.com/user-attachments/assets/78271fc0-0721-4494-a5f0-9c5034a39f71" />
  - キャンセル
    ```
    予約時にIDが表示され、そのIDと電話番号でキャンセルが可能です。
    ```
    <img width="353" height="83" alt="スクリーンショット 2025-09-12 12 15 14" src="https://github.com/user-attachments/assets/f17de898-0f75-4dc2-a4af-e8acdb463042" />
    <img width="1184" height="510" alt="スクリーンショット 2025-09-12 12 15 25" src="https://github.com/user-attachments/assets/ca15fdc2-a51e-4879-ad85-ce2f4278feba" />


  - ログイン(企業,サイト管理者)
    ```
    企業アカウントや管理アカウントでログインができます。
    ```
    <img width="1180" height="748" alt="スクリーンショット 2025-09-12 11 53 25" src="https://github.com/user-attachments/assets/f2ea44f8-cf8e-4bcd-bc11-bd167afd0779" />

- **企業**
  - 自社のバスを一覧表示
    ```
    自社で登録したバスの一覧表示が可能です。
    残席数などを簡単に確認できます。
    ```
  - 新規のバス登録
    ```
    発着場所や発着時間,席数,最終予約時間を入力して
    新しいバスの登録ができます。
    ```
    <img width="1180" height="683" alt="スクリーンショット 2025-09-12 11 53 46" src="https://github.com/user-attachments/assets/24050818-97cc-4879-b1c0-3794085b377b" />

  - ログアウト
    ```
    ログアウトし、ゲストユーザーにもどれます。
    ```

- **サイト管理者**
  - 企業アカウントの管理
    ```
    企業アカウントの一覧表示,追加,削除ができます。
    ```
    <img width="1180" height="587" alt="スクリーンショット 2025-09-12 11 54 11" src="https://github.com/user-attachments/assets/72f9eaee-a474-48d9-b743-e28317e91e71" />

  - 全社のバスの管理(一覧表示,削除)
    ```
    全社のバスの一覧が表示され,削除も可能です。
    ```
    <img width="1167" height="391" alt="スクリーンショット 2025-09-12 11 54 20" src="https://github.com/user-attachments/assets/d93d3a1b-f695-4fce-bca3-3a3a217cc8ac" />

  - 全予約の管理(一覧表示,キャンセル)
    ```
    全社のバスの予約一覧が表示され,削除も可能です。
    ```
    <img width="1184" height="400" alt="スクリーンショット 2025-09-12 12 21 53" src="https://github.com/user-attachments/assets/cacc78ed-2e31-4041-b591-12f99dbb5207" />

  - ログアウト
    ```
    ログアウトし、ゲストユーザーにもどれます。
    ```
# 4.起動について
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
