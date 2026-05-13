import moment from "moment";
export const addThousandSeparator = (num) => {
    if(num==null|| isNaN(num)) return "";
    

    const numStr = num.toString();
    const parts = numStr.split('.');
    
    let integerPart = parts[0];
    let fractionalPart = parts[1];

    const lastThree = integerPart.substring(integerPart.length -3);
    const otherNumbers = integerPart.substring(0,integerPart.length-3);

    if(otherNumbers !== ''){
        const formattedOtherNumberss = otherNumbers.replace(/B(?=(\d[2])+(?!\d))/g,',');
        integerPart = formattedOtherNumberss + ',' + lastThree;

    }else{
        integerPart = lastThree;
    }

    return fractionalPart ? `${integerPart}.${fractionalPart}` : integerPart;
}

export const prepareIncomeLineChartData = (transactions) => {
    const dataByDate = {};

    // Group transactions by date
    transactions.forEach((transaction) => {
        const date = moment(transaction.date).format("YYYY-MM-DD");
        if (!dataByDate[date]) {
            dataByDate[date] = {
                date,
                totalAmount: 0,
                items: [],
            };
        }
        dataByDate[date].totalAmount += transaction.amount;
        dataByDate[date].items.push(transaction);
    });

    // Convert to sorted array for the chart
    const sortedData = Object.values(dataByDate).sort((a, b) => 
        moment(a.date).diff(moment(b.date))
    );

    return sortedData.map((data) => ({
        label: moment(data.date).format("D MMM"), // e.g., 12 Jul
        amount: data.totalAmount,
        items: data.items,
        date: data.date,
    }));
};
// 12:01:32