package ru.fonin.servises;

public class CompanyAddressService {
    // Можно попробовать поиграться с разметкой
    public String getCompanyAddress(){
        StringBuffer str = new StringBuffer();
        str.append("Название компании");
        str.append("\n");
        str.append("Адерс: ");
        str.append("г. Москва улица Кировоградская д. 25");
        str.append("\n");
        str.append("тел.:");
        str.append("+79991112233");
        str.append("\n");
        return  str.toString();
    }
}
