package kr.co._29cm.homework.init;

import kr.co._29cm.homework.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Profile("test")
@Component
@RequiredArgsConstructor
public class InitTestDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit() {
            Item item1 = Item.createItem(768848L, "[STANLEY] GO CERAMIVAC 진공 텀블러/보틀 3종", 21000, 100000);
            Item item2 = Item.createItem(748943L, "디오디너리 데일리 세트 (Daily set)", 19000, 100000);
            Item item3 = Item.createItem(779989L, "버드와이저 HOME DJing 굿즈 세트", 35000, 100000);
            Item item4 = Item.createItem(779943L, "Fabrik Pottery Flat Cup & Saucer - Mint", 24900, 100000);
            Item item5 = Item.createItem(768110L, "네페라 손 세정제 대용량 500ml 이더블유지", 7000, 100000);
            Item item6 = Item.createItem(517643L, "에어팟프로 AirPods PRO 블루투스 이어폰(MWP22KH/A)", 260800, 100000);
            Item item7 = Item.createItem(706803L, "ZEROVITY™ Flip Flop Cream 2.0 (Z-FF-CRAJ-)", 38000, 100000);
            Item item8 = Item.createItem(759928L, "마스크 스트랩 분실방지 오염방지 목걸이", 2800, 100000);
            Item item9 = Item.createItem(213341L, "20SS 오픈 카라/투 버튼 피케 티셔츠 (6color)", 33250, 100000);
            Item item10 = Item.createItem(377169L, "[29Edition.]_[스페셜구성] 뉴코튼베이직 브라렛 세트 (브라1+팬티2)", 24900, 100000);
            Item item11 = Item.createItem(744775L, "SHUT UP [TK00112]", 28000, 100000);
            Item item12 = Item.createItem(779049L, "[리퍼브/키친마켓] Fabrik Pottery Cup, Saucer (단품)", 10000, 100000);
            Item item13 = Item.createItem(611019L, "플루크 new 피그먼트 오버핏 반팔티셔츠 FST701 / 7color M", 19800, 100000);
            Item item14 = Item.createItem(628066L, "무설탕 프로틴 초콜릿 틴볼스", 12900, 100000);
            Item item15 = Item.createItem(502480L, "[29Edition.]_[스페셜구성] 렉시 브라렛 세트(브라1+팬티2)", 24900, 100000);
            Item item16 = Item.createItem(782858L, "폴로 랄프로렌 남성 수영복반바지 컬렉션 (51color)", 39500, 100000);
            Item item17 = Item.createItem(760709L, "파버카스텔 연필1자루", 200, 100000);
            Item item18 = Item.createItem(778422L, "캠핑덕 우드롤테이블", 45000, 100000);
            Item item19 = Item.createItem(648418L, "BS 02-2A DAYPACK 26 (BLACK)", 238000, 100000);

            em.persist(item1);
            em.persist(item2);
            em.persist(item3);
            em.persist(item4);
            em.persist(item5);
            em.persist(item5);
            em.persist(item6);
            em.persist(item7);
            em.persist(item8);
            em.persist(item9);
            em.persist(item10);
            em.persist(item11);
            em.persist(item12);
            em.persist(item13);
            em.persist(item14);
            em.persist(item15);
            em.persist(item16);
            em.persist(item17);
            em.persist(item18);
            em.persist(item19);
        }
    }
}
