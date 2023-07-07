package org.tukorea.ticketbox.util;

import java.util.*;
import org.tukorea.ticketbox.payment.*;

public class Statistics {
	// 스크린별 결제 금액 총액 계산
	public static double sum(HashMap<Integer, Receipt> map) {
		Set<Integer> keys = map.keySet();
		Iterator<Integer> it = keys.iterator();
		double sum = 0;
		
		while(it.hasNext()) {
			Integer id = it.next();
			Receipt r = map.get(id);
			sum += r.getTotalAmount();
		}

		return sum;
	}
}
