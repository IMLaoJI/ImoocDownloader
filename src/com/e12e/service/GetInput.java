package com.e12e.service;

import java.util.Scanner;

public class GetInput {
	public static int getInputClassNo() {
		int classNo = 0;
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out
			.println("��������Ҫ���صĿγ̱�ţ��磺http://www.imooc.com/learn/601 �� http://www.imooc.com/view/601��������601����");
			try {
				classNo = scanner.nextInt();
			} catch (Exception e) {
				System.out.println("�γ̱����д����ֻ������������\n");
				scanner.nextLine();
				continue;
			}
			return classNo;
		}
	}

	public static int getInputVideoDef() {
		int videoDef = 0;
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("������Ҫ���ص������ȣ���0�����壬��1�����壬��2�����壺");
			try {
				videoDef = scanner.nextInt();
				if (videoDef > 2 || videoDef < 0) {
					throw new Exception();
				}
			} catch (Exception e) {
				System.out.println("ֻ�����롾0����1����2���е�һ������\n");
				scanner.nextLine();
				continue;
			}
			return videoDef;
		}
	}
}
