# reactor practice

1. ["Blenders", "Old", "Johnnie"] 와 "[Pride", "Monk", "Walker”] 를 순서대로 하나의 스트림으로 처리되는 로직 검증
2. 1~100 까지의 자연수 중 짝수만 출력하는 로직 검증
3. “hello”, “there” 를 순차적으로 publish하여 순서대로 나오는지 검증
4. 아래와 같은 객체가 전달될 때 “JOHN”, “JACK” 등 이름이 대문자로 변환되어 출력되는 로직 검증
    ```
    Person("John", "[john@gmail.com](mailto:john@gmail.com)", "12345678")
    Person("Jack", "[jack@gmail.com](mailto:jack@gmail.com)", "12345678")
    ```
5. ["Blenders", "Old", "Johnnie"] 와 "[Pride", "Monk", "Walker”]를 압축하여 스트림으로 처리 검증
    ```
    예상되는 스트림 결과값 ["Blenders Pride", "Old Monk", "Johnnie Walker”]
    ```
6. ["google", "abc", "fb", "stackoverflow”] 의 문자열 중 5자 이상 되는 문자열만 대문자로 비동기로 치환하여 1번 반복하는 스트림으로 처리하는 로직 검증
    ```
    예상되는 스트림 결과값 ["GOOGLE", "STACKOVERFLOW", "GOOGLE", "STACKOVERFLOW"]
    ```
