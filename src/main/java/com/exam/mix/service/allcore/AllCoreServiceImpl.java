package com.exam.mix.service.allcore;

import com.exam.mix.dto.core.AllCoreDTO;
import com.exam.mix.entity.core.Allcore;
import com.exam.mix.entity.core.gubun.*;
import com.exam.mix.repository.core.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class AllCoreServiceImpl implements AllCoreService {

    private final AllCoreRepository allCoreRepository;
    private final MetalRepository metalRepository;
    private final BeastRepository beastRepository;
    private final BirdRepository birdRepository;
    private final BugRepository bugRepository;
    private final DavilRepository davilRepository;
    private final DragonRepository dragonRepository;
    private final MysteryRepository mysteryRepository;
    private final PlantRepository plantRepository;

    //공백용 코어
    private static final AllCoreDTO MAIN_SUB_CORE_X = AllCoreDTO.builder()
            .core_name("x")
            .main_core("x")
            .sub_core("x")
            .max_lv(0)
            .min_lv(0)
            .build();

    public static final int METAL = 0;
    public static final int BIRD = 1;
    public static final int PLANT = 2;
    public static final int DAVIL = 3;
    public static final int BEAST = 4;
    public static final int BUG = 5;
    public static final int DRAGON = 6;
    public static final int MYSTERY = 7;

    @Override
    public List<String> getList() {
        List<Allcore> all = allCoreRepository.findAll();

        return all.stream()
                .filter(core -> !("메탈".equals(core.getCore_name()) || ("새".equals(core.getCore_name())) || "드래곤".equals(core.getCore_name()) || "식물".equals(core.getCore_name()) || "미스터리".equals(core.getCore_name()) || "악마".equals(core.getCore_name()) || "짐승".equals(core.getCore_name()) || "곤충".equals(core.getCore_name())))
                .map(Allcore::getCore_name)
                .collect(Collectors.toList());
    }





    public List<AllCoreDTO> find(String core_name){
        System.out.println("AllCoreDTO find 실행");

        //클라이언트에서 받은 이름가져와서 id 가져오기
        Optional<Allcore> targetCore = allCoreRepository.findByCore_name(core_name);

        log.info("id와 코어 이름 저장 성공 : " + targetCore);

        //클라이언트로 보낼 배열생성
        List<AllCoreDTO> allCoreList = new ArrayList<>();//List는 인터페이스라 따로 구현체가 필요하고 + 동적배열이다.LinkedList등 총4가지 구현체사용가능
        //처음 목표 몬스터의 이름의 정보로 하위 쟤료들을 가져온다.
        allCoreList.add(CoreGubun(targetCore.get().getCore_id(), targetCore.get().getCore_name()));
        log.info(allCoreList);

        //트리구조에서 카운트 할때 사용 알고리즘을 모름
        int[] count_I_P1 = {1, 2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14};
        int[] count_I_P2 = {2, 3, 4, 5, 6, 7, 8, 10, 11, 12, 13, 14, 15};
        int k = -2; // 메인쟤료와 서브쟤료 구할때 카운트를위해 생성

        //맨처음 총정보 구하고 메인 서브 구하고, 그 메인자리에서 또 메인 서브 구하고 반복
        for (int i= 0; i < 15; i++){
            k +=1;
                try{
                    //2계층 부터 메인과 서브 둘다 없을경우
                    if (allCoreList.get(k+1).getMain_core().equals("x") && allCoreList.get(k+1).getSub_core().equals("x")){
                        allCoreList.add(count_I_P1[i]+i, MAIN_SUB_CORE_X);
                        allCoreList.add(count_I_P2[i]+i, MAIN_SUB_CORE_X);
                        System.out.println("둘다 없을때" + allCoreList.get(k+1).getCore_name());
                        continue;
                    }
                    //메인자리에 넣기
                    AllCoreDTO mainCore = AllCoreDTO.builder()
                                    .core_name(allCoreList.get(k+1).getMain_core())//0번째에서 메인쟤료가져오기
                                    .build();
                    log.info(allCoreList);
                    log.info("mainCore = " + mainCore.getCore_name().equals("x") + mainCore.getCore_name());
                    //메인 쟤료가 있는지 체크 후 없으면 else 실행
                    if (!mainCore.getCore_name().equals("x")){
                        String[] splitMain_core_name = mainCore.getCore_name().split("\\("); //[0]배열에는 (전까이의 이름이,[1]에는 이후 문자가들어있다.
                        Optional<Allcore> searchMainCore = allCoreRepository.findByCore_name(splitMain_core_name[0]); // 쿼리 해서 결과값을 받아옴
                        allCoreList.add(count_I_P1[i]+i, CoreGubun(searchMainCore.get().getCore_id(), searchMainCore.get().getCore_name()));//메인쟤료 가져와서 1번째에 대입(자리수,매개변수)
                        System.out.println("k = " + k);
                        System.out.println("allCoreList (i+1) = " + allCoreList.get(i+1));
                    }
                    else {
                        allCoreList.add(count_I_P1[i]+i, MAIN_SUB_CORE_X);
                    }



                    //서브자리에 넣기
                    AllCoreDTO subCore = AllCoreDTO.builder()
                                    .core_name(allCoreList.get(k+1).getSub_core())//0번째에서 서브쟤료가져오기
                                    .build();
                    System.out.println("aubCore = " + subCore.getCore_name().equals("x") + subCore.getCore_name());
                    //만약 서브가 x이면 패스
                    if (!subCore.getCore_name().equals("x"))
                    {
                        String[] splitSub_Core_Name = subCore.getCore_name().split("\\("); //[0]배열에는 (전까이의 이름이,[1]에는 이후 문자가들어있다.
                        Optional<Allcore> searchSubCore = allCoreRepository.findByCore_name(splitSub_Core_Name[0]);
                        allCoreList.add(count_I_P2[i]+i, CoreGubun(searchSubCore.get().getCore_id(), searchSubCore.get().getCore_name()));//서브쟤료 가져와서 1번째에 대입
                        System.out.println("allCoreList (i+2) = " + allCoreList.get(i+2));
                        System.out.println("allCoreList = " + allCoreList);
                    }
                    else {
                        allCoreList.add(count_I_P2[i]+i, MAIN_SUB_CORE_X);
                    }

                }
                catch (NullPointerException | IndexOutOfBoundsException e){
                    break;
                }
            }
            return allCoreList;


}

    //id와 name을 가지고와서 코어의 메인, 서브 쟤료를 가져온다.
    private AllCoreDTO CoreGubun(Long core_id, String core_name){ //core_id로 받고 검색은 name으로.
        AllCoreDTO allcore = null;
        switch ((int) (core_id / 200)){//200단위로 속성끊으니까 200으로나눠서 원하는 쿼리 보내기
            case METAL:
                Metal metal = Metal.builder().core_name(core_name).build();
                metal = metalRepository.findById(metal.getCore_name()).get();
                 return allcore = AllCoreDTO.builder()
                         .core_id(core_id)
                         .core_name(metal.getCore_name())
                         .main_core(metal.getMain_core())
                         .sub_core(metal.getSub_core())
                         .max_lv(metal.getMax_lv())
                         .min_lv(metal.getMin_lv())
                         .build();
            case BIRD:
                Bird bird = Bird.builder().core_name(core_name).build();
                bird = birdRepository.findById(bird.getCore_name()).get();
                return allcore = AllCoreDTO.builder()
                        .core_id(core_id)
                        .core_name(bird.getCore_name())
                        .main_core(bird.getMain_core())
                        .sub_core(bird.getSub_core())
                        .max_lv(bird.getMax_lv())
                        .min_lv(bird.getMin_lv())
                        .build();
            case PLANT:
                Plant plant = Plant.builder().core_name(core_name).build();
                plant = plantRepository.findById(plant.getCore_name()).get();
                return allcore = AllCoreDTO.builder()
                        .core_id(core_id)
                        .core_name(plant.getCore_name())
                        .main_core(plant.getMain_core())
                        .sub_core(plant.getSub_core())
                        .max_lv(plant.getMax_lv())
                        .min_lv(plant.getMin_lv())
                        .build();
            case DAVIL:
                Davil davil = Davil.builder().core_name(core_name).build();
                davil = davilRepository.findById(davil.getCore_name()).get();
                return allcore = AllCoreDTO.builder()
                        .core_id(core_id)
                        .core_name(davil.getCore_name())
                        .main_core(davil.getMain_core())
                        .sub_core(davil.getSub_core())
                        .max_lv(davil.getMax_lv())
                        .min_lv(davil.getMin_lv())
                        .build();
            case BEAST:
                Beast beast = Beast.builder().core_name(core_name).build();
                beast = beastRepository.findById(beast.getCore_name()).get();
                return allcore = AllCoreDTO.builder()
                        .core_id(core_id)
                        .core_name(beast.getCore_name())
                        .main_core(beast.getMain_core())
                        .sub_core(beast.getSub_core())
                        .max_lv(beast.getMax_lv())
                        .min_lv(beast.getMin_lv())
                        .build();
            case BUG:
                Bug bug = Bug.builder().core_name(core_name).build();
                bug = bugRepository.findById(bug.getCore_name()).get();
                return allcore = AllCoreDTO.builder()
                        .core_id(core_id)
                        .core_name(bug.getCore_name())
                        .main_core(bug.getMain_core())
                        .sub_core(bug.getSub_core())
                        .max_lv(bug.getMax_lv())
                        .min_lv(bug.getMin_lv())
                        .build();
            case DRAGON:
                Dragon dragon = Dragon.builder().core_name(core_name).build();
                dragon = dragonRepository.findById(dragon.getCore_name()).get();
                return allcore = AllCoreDTO.builder()
                        .core_id(core_id)
                        .core_name(dragon.getCore_name())
                        .main_core(dragon.getMain_core())
                        .sub_core(dragon.getSub_core())
                        .max_lv(dragon.getMax_lv())
                        .min_lv(dragon.getMin_lv())
                        .build();
            case MYSTERY:
                Mystery mystery = Mystery.builder().core_name(core_name).build();
                mystery = mysteryRepository.findById(mystery.getCore_name()).get();
                return allcore = AllCoreDTO.builder()
                        .core_id(core_id)
                        .core_name(mystery.getCore_name())
                        .main_core(mystery.getMain_core())
                        .sub_core(mystery.getSub_core())
                        .max_lv(mystery.getMax_lv())
                        .min_lv(mystery.getMin_lv())
                        .build();
        }
        return allcore;
    }
}
