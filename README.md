# ThucHanhIE303.Q22

### LAB1 - Thực hành làm quen với ngôn ngữ Java

#### Câu 1: Tính diện tích hình tròn không dùng bất kỳ hằng số nào.

- Ý tưởng: Sử dụng phương pháp xác suất của Monte Carlo, chọn ngẫu nhiên 1 triệu điểm trên hình vuông bao quanh đường tròn tâm O (0, 0), sau đó tính ra số lượng điểm nằm bên trong đường tròn chia cho số điểm đã ném ra, nhân với diện tích hình vuông sẽ ra xấp xỉ diện tích hình tròn.

#### Câu 2: 

#### Câu 3: 

#### Câu 4:

### LAB2 - Xây dựng mô phỏng Game FlappyBird

- Ý tưởng: Xây dựng giao diện có sẵn với Java Swing và thư viện java.awt để vẽ và xử lý sự kiện

#### Câu 1: Xây dựng cửa sổ Flappy Bird và đặt ảnh nền.
- Cách thực hiện: Sử dụng JFrame để vẽ khung cố định 360x640 cho game. Dùng `setResizable(false)` để khóa bố cục game lại, tránh ảnh hưởng tọa độ.
- Code thực thi:
```
 setTitle("Flappy Bird");

    setSize(360, 640);

    setResizable(false);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    ImageIcon bgIcon = new ImageIcon("flappybirdbg.png");
    JLabel background = new JLabel(bgIcon);

    setContentPane(background);
    background.setLayout(new BorderLayout());

    setLocationRelativeTo(null);
    setVisible(true);
```

#### Câu 2: Khởi tạo đối tượng Bird và xử lý lên xuống.
- Ý tưởng: Xây dựng mô phỏng trọng lượng vật lý để chim có thể rơi xuống. Khi người dùng bấm Space hoặc Enter, tiến hành ghi đè vận tốc rơi bằng một số (Trong bài này là -9) và thực thi trong hệ trục tọa độ.
- Cách thực hiện: Khởi tạo một class có tên Bird với các thuộc tính: Tọa độ x, tọa độ y, chiều dài và chiều rộng của bird (Sử dụng Graphics trong awt để vẽ bird bằng ảnh có sẵn). Sau đó, viết các phương thức move và xử lý bàn phím để thực hiện lên xuống của bird.
```
 class Bird {
        int x = birdX, y = birdY, width = birdWidth, height = birdHeight;
        Image img;
        Bird(Image img) { this.img = img; }
    }
```

#### Câu 3: Thiết lập gameloop và Pipe cho trò chơi
- Ý tưởng: Xây dựng vòng lặp game chạy liên tục để tính toán lại vị trí của chim và cột để thực hiện vẽ lại màn hình. Đối với Pipe, xây dựng các hàm để tính toán và tạo ngẫu nhiên vị trí Y của các cột để chim có thể bay qua.
- Cách thực hiện: Khởi tạo class Pipe và ArrayList để lưu vị trí các cột, xây dựng hàm tính toán cột trên bằng `Math.random()` và tạo khoảng trống bằng 0.3 chiều cao của màn hình để chim có thể bay qua. Sau đó, viết tiếp các hàm kiểm tra tọa độ của bird và cột để xem bird có đụng phải cột hoặc đã đi qua các cột hay chưa.

#### Câu 4: Thực hiện cơ chế tính điểm và restart trò chơi
- Ý tưởng: Tạo biến đếm điểm cho trò chơi, kết hợp với nút trên bàn phím khi có logic đụng độ hoặc chim rơi xuống đất, trong trường hợp trên trò chơi sẽ kết thúc, điểm và vị trí bird sẽ reset về lại 0. Ngoài ra, để tăng độ khó trò chơi, mỗi pipe khi dưới 10 điểm sẽ dịch chuyển 1 khoảng thấp, sau đó sẽ dịch chuyển nhanh hơn để người chơi khó bay qua.
- Cách thực hiện: Tạo biến `score` và `gameOver` để quản lý điểm và vận hành trò chơi. Khi trò chơi kết thúc, kiểm tra gameOver và nếu có sự kiện từ bàn phím (Trong trường hợp này là nút R) sẽ restart trò chơi.
