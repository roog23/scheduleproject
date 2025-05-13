# api 명세서
![api](https://github.com/user-attachments/assets/b1df168e-429e-42ca-9236-956ba9da48fd)


# ERD 
![ERD](https://github.com/user-attachments/assets/bcff2d41-ce61-4788-a826-ee00fd339f1b)

# 프로그램 실행 방식
1. Request가 들어오면 Controller에서 요청에 맞는 로직을 실행합니다. 
(Request에 조건이 필요한 경우 @Valid를 사용하여 요구사항에 적합하지 않은 경우 예외 처리를 진행합니다.)
2. Service에서 요청을 수행하기 위한 동작을 하고, 데이터 조회나 저장이 필요한 경우 Repository에 요청합니다.
3. Repository에서 쿼리를 실행해 필요한 데이터를 Service에 반환합니다.
4. Service에서 데이터를 받아 예외 처리를 진행하고, 요청을 처리 결과를 Controller에 반환합니다.
5. Controller에서 요청 처리 결과를 Http Response body에 담아 전달합니다.
