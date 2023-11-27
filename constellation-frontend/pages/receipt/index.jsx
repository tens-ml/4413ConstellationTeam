import Button from "@/components/Button";
import Paper from "@/components/Paper";
import Shell from "@/components/Shell";

const Receipt = () => {
  return (
    <Shell>
      <Paper width="800px" className="mt-32 flex flex-col">
        <p className="mr-16 text-center font-semibold mt-2 font-sans text-3xl text-gray-800">
          Payment
        </p>
        <form className="flex mt-8">
          <div className="grow pr-6 border-r-2 border-gray-300">
            <p className="border-b-2 border-gray-300 text-md text-center">
              Receipt
            </p>
            <div className="flex flex-col mt-4">
              <p className="text-gray-700 font-medium">
                <b>First Name:</b> test name
              </p>
              <p className="text-gray-700 font-medium">
                <b>Last Name</b> test last
              </p>
              <p className="text-gray-700 font-medium">
                <b>Street:</b> 100
              </p>
              <p className="text-gray-700 font-medium">
                <b>Number</b> 10
              </p>
              <p className="text-gray-700 font-medium">
                <b>Province:</b>
              </p>
              <p className="text-gray-700 font-medium">
                <b>Country:</b> Canada
              </p>
              <p className="text-gray-700 font-medium">
                <b>Postal Code:</b> 333
              </p>
              <p className="text-gray-700 font-medium">
                <b>Total Paid:</b> $110
              </p>
              <p className="text-gray-700 font-medium">
                <b>Item ID:</b> 300
              </p>
              <p className="text-gray-700 font-medium">
                <b>Item Name:</b> something
              </p>
            </div>
          </div>
          <div className="grow pl-6 w-1/2">
            <p className="border-b-2 border-gray-300 text-md text-center">
              Shipping Details
            </p>
            <p className="mt-12">
              Your item should ship within the next <b>3</b> business days.
            </p>
          </div>
        </form>
        <Button className="mt-8 w-full" onClick={() => router.back()}>
          Back
        </Button>
      </Paper>
    </Shell>
  );
};

export default Receipt;
