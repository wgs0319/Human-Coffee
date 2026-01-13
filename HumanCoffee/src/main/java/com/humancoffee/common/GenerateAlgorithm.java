package com.humancoffee.common;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GenerateAlgorithm {

	public final byte	DEF_SEARCH_INDEX_POS = 0;
	public final byte	DEF_SEARCH_RESULT_POS = 1;
	
	
	/**
     * 사용자 ID와 패스워드를 이용하여 SHA-256 해시 값을 생성합니다.
     * 사용자 ID를 솔트로 사용합니다.
     *
     * @param id 사용자 ID (솔트로 사용됨)
     * @param pwd 사용자의 평문 패스워드
     * @return SHA-256 해시 값 (16진수 문자열), 오류 발생 시 null
     */
	public String generateSha256(String id, String pwd) {
		String saltedPwd = id + pwd;
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(saltedPwd.getBytes(StandardCharsets.UTF_8));
			
			StringBuilder hexString = new StringBuilder();
			for(byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				if(hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private static <T> int partition(List<T> list, int low, int high, Comparator<? super T> comparator) {
		T pivot = list.get(high);
		int i = (low - 1);
		
		for(int j = low; j < high; j++) {
			if(comparator.compare(list.get(j), pivot) <= 0) {
				i++;
				Collections.swap(list, i, j);
			}
		}
		Collections.swap(list, i + 1, high);
		return i + 1;
	}
	
	private static <T> void quickSort(List<T> list, int low, int high, Comparator<? super T> comparator){
		if(low < high) {
			int partitionIndex = partition(list, low, high, comparator);
			quickSort(list, low, partitionIndex - 1, comparator);
			quickSort(list, partitionIndex + 1, high, comparator);
		}
	}
	/**
     * 주어진 리스트를 Comparator를 사용하여 퀵 정렬하는 메소드
     * @param <T> 정렬할 리스트의 요소 타입
     * @param list 정렬할 리스트
     * @param comparator 정렬 기준을 정의하는 Comparator
     */
	public static <T> void quickSort(List<T> list, Comparator<? super T> comparator) {
		if(list == null || list.size() <= 1)
			return;
		quickSort(list, 0, list.size() - 1, comparator);
	}
	
	/*	sample class
	 public class Student implements Comparable<Student>{
	 	private String name;
	 	private int age;
	 	
	 	public Studnet(String name, int age){
	 		this.name = name;
	 		this.age = age;
	 	}
	 	
	 	public String getName(){
	 		return this.name;
	 	}
	 	
	 	public int getAge(){
	 		return this.age;
	 	}
	 
     
     	//	Comparable 인터페이스 구현: 나이를 기준으로 오름차순 정렬
	 	@Override
	 	public int compareTo(Student other){
	 		return Integer.compare(this.age, other.age);
	 	}
	 }*/
	
	/*	sample main class
	 import ???.Student;
	 import ???.GenerateAlgorithm;
	 
	 public static void main(String[] args){
	 	List<Studnet> students = new ArrayList<>();
	 	students.add(new Student("Alice", 25));
        students.add(new Student("Charlie", 22));
        students.add(new Student("Bob", 28));
        students.add(new Student("David", 25));

        System.out.println("정렬 전: " + students);

        // 정렬 기준을 정의하는 Comparator 생성
        // 여기서는 Student 객체의 나이를 기준으로 정렬
        Comparator<Student> ageComparator = new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return Integer.compare(s1.getAge(), s2.getAge());
            }
        };

        // 또는 람다식으로 간결하게 작성 가능 (Java 8 이상)
        // Comparator<Student> ageComparator = (s1, s2) -> Integer.compare(s1.getAge(), s2.getAge());

        // 다른 패키지의 Algorithm 클래스에 있는 quickSort() 메소드 호출
        // com.example.sorting.Algorithm.quickSort(students, ageComparator);
        Algorithm.quickSort(students, ageComparator);

        System.out.println("정렬 후 (나이 기준): " + students);
	 }
	 */
	
	/*	quickSort 사용법 */
	
	 /**
     * 변형된 이진 탐색을 수행하여 인덱스와 상태 값을 반환하는 메소드 (제네릭 버전).
     *
     * @param <T>         리스트의 요소 타입
     * @param sortedList  정렬된 리스트
     * @param target      찾으려는 값
     * @param comparator  정렬 기준을 정의하는 Comparator
     * @return 길이가 2인 int 배열. [인덱스, 상태]를 의미.
     * - 상태: 0 (값 찾음), -1 (값 없음, 왼쪽에 삽입), 1 (값 없음, 오른쪽에 삽입)
     */
    public <T> int[] binarySearchIndex(List<T> sortedList, T target, Comparator<? super T> comparator) {
        if (sortedList == null || sortedList.isEmpty()) {
            return new int[]{-1, -1};
        }

        int low = 0;
        int high = sortedList.size() - 1;
        int lastCheckedIndex = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            T midValue = sortedList.get(mid);
            
            lastCheckedIndex = mid;
            
            int cmp = comparator.compare(midValue, target);

            if (cmp == 0) {
                return new int[]{mid, 0}; // 목표값 발견
            } else if (cmp < 0) {
                low = mid + 1; // 타겟이 더 큼 -> 오른쪽으로
            } else {
                high = mid - 1; // 타겟이 더 작음 -> 왼쪽으로
            }
        }

        // 값이 없을 때의 로직
        int cmp = comparator.compare(sortedList.get(lastCheckedIndex), target);
        if (cmp < 0) {
            return new int[]{lastCheckedIndex, 1};
        } else {
            return new int[]{lastCheckedIndex, -1};
        }
    }
    
    public <T> T binarySearchObj(List<T> sortedList, T target, Comparator<? super T> comparator) {
        if (sortedList == null || sortedList.isEmpty()) {
        	return null;
//            return new int[]{-1, -1};
        }

        int low = 0;
        int high = sortedList.size() - 1;
//        int lastCheckedIndex = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            T midValue = sortedList.get(mid);
            
//            lastCheckedIndex = mid;
            
            int cmp = comparator.compare(midValue, target);

            if (cmp == 0) {
            	return midValue;
//                return new int[]{mid, 0}; // 목표값 발견
            } else if (cmp < 0) {
                low = mid + 1; // 타겟이 더 큼 -> 오른쪽으로
            } else {
                high = mid - 1; // 타겟이 더 작음 -> 왼쪽으로
            }
        }
        return null;
        
        // 값이 없을 때의 로직
        /*
        int cmp = comparator.compare(sortedList.get(lastCheckedIndex), target);
        if (cmp < 0) {
            return new int[]{lastCheckedIndex, 1};
        } else {
            return new int[]{lastCheckedIndex, -1};
        }*/
    }
    
    /*	sample main class
	 import ???.Student;
	 import ???.GenerateAlgorithm;
	 
	 public static void main(String[] args) {
        // 정렬할 Student 객체 리스트 생성
        List<Student> students = new ArrayList<>();
        students.add(new Student("David", 25));
        students.add(new Student("Charlie", 22));
        students.add(new Student("Alice", 25));
        students.add(new Student("Bob", 28));
        students.add(new Student("Frank", 30));
        students.add(new Student("Edward", 26));

        // 나이(age)를 기준으로 정렬하는 Comparator
        Comparator<Student> ageComparator = Comparator.comparingInt(Student::getAge);

        // **1. 이진 탐색을 위해 먼저 리스트를 정렬해야 함**
        // quickSort 메소드가 Student 리스트를 정렬합니다.
        Algorithm.quickSort(students, ageComparator);
        System.out.println("정렬 후 (나이 기준): " + students);
        
        System.out.println("-------------------------------------");

        // **2. 찾으려는 값 지정**
        // 목표값을 Student 객체로 생성합니다.
        // 이 때, 비교에 필요한 속성(age)만 중요합니다. name은 비교에 사용되지 않음.
        Student targetStudent1 = new Student("Unknown", 25); // 존재하는 값
        Student targetStudent2 = new Student("Unknown", 24); // 존재하지 않는 값 (22와 25 사이)
        Student targetStudent3 = new Student("Unknown", 32); // 존재하지 않는 값 (30보다 큼)
        
        // **3. 변형된 이진 탐색 수행**
        int[] result1 = Algorithm.search(students, targetStudent1, ageComparator);
        System.out.printf("목표값: %d, 결과: 인덱스 %d, 상태 %d\n", 
            targetStudent1.getAge(), result1[0], result1[1]);

        int[] result2 = Algorithm.search(students, targetStudent2, ageComparator);
        System.out.printf("목표값: %d, 결과: 인덱스 %d, 상태 %d\n", 
            targetStudent2.getAge(), result2[0], result2[1]);

        int[] result3 = Algorithm.search(students, targetStudent3, ageComparator);
        System.out.printf("목표값: %d, 결과: 인덱스 %d, 상태 %d\n", 
            targetStudent3.getAge(), result3[0], result3[1]);
    }
	 */
	
}
